package com.tweetapp.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.tweetapp.entity.LoginResponse;
import com.tweetapp.entity.User;
import com.tweetapp.exception.InvalidEmailException;
import com.tweetapp.exception.InvalidUsernameOrPasswordException;
import com.tweetapp.exception.NoSuchUserException;
import com.tweetapp.exception.UserAlreadyExistsException;
import com.tweetapp.exception.UserEmailAlreadyInUseException;
import com.tweetapp.jwt.JwtUtil;
import com.tweetapp.pojo.LoginUser;
import com.tweetapp.pojo.ResetPasswordUser;
import com.tweetapp.pojo.UserResponse;
import com.tweetapp.repository.UserRepository;

public class UserServiceImplTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	JwtUtil jwtUtil;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	private User user1;
	private UserResponse userResponse1;
	private LoginUser loginUser1;
	private LoginUser loginWrongPassUser1;
	private LoginResponse loginResponse1;
	private ResetPasswordUser resetPasswordUser1;
	
	@BeforeEach
	public void setUp() {
		user1 = new User("john", "John", "Wick", "john@yahoo.com", "john12", "1234567890");
		userResponse1 = new UserResponse("john", "John", "Wick", "john@yahoo.com", "1234567890");
		loginUser1 = new LoginUser("john", "john12");
		loginWrongPassUser1 = new LoginUser("john", "john1");
		loginResponse1 = new LoginResponse("john", "abcndjskd");
		resetPasswordUser1 = new ResetPasswordUser("john@yahoo.com", "john1");
	}
	
	
	/***************************************  registerUser()  **************************************************/
	
	@Test
	@DisplayName("Test registerUser() with valid user")
	public void testRegisterUserValid() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		when(userRepo.save(user1)).thenReturn(user1);
		assertThat(userService.registerUser(user1).getUsername()).isEqualTo(userResponse1.getUsername());
		verify(userRepo).findById("john");
	}
	
	@Test
	@DisplayName("Test registerUser() with existing user")
	public void testRegisterUserExists() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user1));
		assertThrows(UserAlreadyExistsException.class, () -> { userService.registerUser(user1); });
		verify(userRepo).findById("john");
	}
	
	@Test
	@DisplayName("Test registerUser() with already used email")
	public void testRegisterUserAlreadyUsedEmail() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		when(userRepo.findByEmail("john@yahoo.com")).thenReturn(user1);
		assertThrows(UserEmailAlreadyInUseException.class, () -> { userService.registerUser(user1); });
		verify(userRepo).findById("john");
		verify(userRepo).findByEmail("john@yahoo.com");
	}
	
	
	/********************************************  loginUser()  ********************************************************/
	
	
	@Test
	@DisplayName("Test loginUser() with valid user")
	public void testLoginUserValid() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user1));
		when(jwtUtil.generateToken("john")).thenReturn("abcndjskd");
		assertThat(userService.loginUser(loginUser1).getUsername()).isEqualTo(loginResponse1.getUsername());
		assertThat(userService.loginUser(loginUser1).getToken()).isEqualTo(loginResponse1.getToken());
		verify(userRepo,times(2)).findById("john");
	}

	
	@Test
	@DisplayName("Test loginUser() with non existing user")
	public void testRegisterUserNotExists() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(InvalidUsernameOrPasswordException.class, () -> { userService.loginUser(loginUser1); });
		verify(userRepo).findById("john");
	}
	
	@Test
	@DisplayName("Test loginUser() with wrong password user")
	public void testRegisterWrongPasswordUser() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user1));
		assertThrows(InvalidUsernameOrPasswordException.class, () -> { userService.loginUser(loginWrongPassUser1); });
		verify(userRepo).findById("john");
	}
	
	@Test
	@DisplayName("Test loginUser() with null password user")
	public void testRegisterNullPasswordUser() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user1));
		assertThrows(InvalidUsernameOrPasswordException.class, () -> { userService.loginUser(new LoginUser("john",null)); });
		verify(userRepo).findById("john");
	}
	
	@Test
	@DisplayName("Test loginUser() with null user")
	public void testRegisterNullUser() throws Exception {
		assertThrows(InvalidUsernameOrPasswordException.class, () -> { userService.loginUser(new LoginUser(null,"john12")); });
	}
	
	
	/***********************************************  validateToken()  **********************************************************/
	
	@Test
	@DisplayName("Test validateToken() with valid token")
	public void testvalidateTokenValid() throws Exception {
		when(jwtUtil.isTokenValid("abcndjskd")).thenReturn(true);
		assertTrue(userService.validateToken("abcndjskd"));
		verify(jwtUtil).isTokenValid(any());
	}
	
	@Test
	@DisplayName("Test validateToken() with Invalid token")
	public void testvalidateTokenInvalid() throws Exception {
		when(jwtUtil.isTokenValid("abc")).thenReturn(false);
		assertFalse(userService.validateToken("abc"));
		verify(jwtUtil).isTokenValid(any());
	}
	
	
	/***************************************************  resetPasswordUser()  ****************************************************/
	
	@Test
	@DisplayName("Test resetPasswordUser() with valid user")
	public void testResetPasswordUserValid() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user1));
		assertThat(userService.resetPasswordUser("john",resetPasswordUser1).getMessage()).isEqualTo("Password Reset Successful");
		verify(userRepo).findById(any());
	}
	
	@Test
	@DisplayName("Test resetPasswordUser() with non existing user")
	public void testResetPasswordUserNotExists() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(NoSuchUserException.class, () -> { userService.resetPasswordUser("john", resetPasswordUser1); });
		verify(userRepo).findById("john");
	}
	
	@Test
	@DisplayName("Test resetPasswordUser() with not matching email")
	public void testResetPasswordUserNotMatchingEmail() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user1));
		assertThrows(InvalidEmailException.class, () -> { userService.resetPasswordUser("john", new ResetPasswordUser("john1@yahoo.com", "john1")); });
		verify(userRepo).findById("john");
	}
	
	@Test
	@DisplayName("Test resetPasswordUser() with null user")
	public void testResetPasswordUserNullUser() throws Exception {
		assertThrows(InvalidUsernameOrPasswordException.class, () -> { userService.resetPasswordUser(null, resetPasswordUser1); });
	}
	
	
	/*******************************************  findAllUsers()  ************************************************/
	
	@Test
	@DisplayName("Test findAllUsers()")
	public void testFindAllUsersValid() throws Exception {
		when(userRepo.findAll()).thenReturn(List.of(user1));
		assertThat(userService.findAllUsers()).hasSize(1);
		verify(userRepo).findAll();
	}
	
	@Test
	@DisplayName("Test findAllUsers() with empty users")
	public void testFindAllUsersEmptyInvalid() throws Exception {
		when(userRepo.findAll()).thenReturn(List.of());
		assertThrows(NoSuchUserException.class,() -> { userService.findAllUsers(); });
		verify(userRepo).findAll();
	}
	
	
	/****************************************** searchByUsername()  ********************************************************/
	
	@Test
	@DisplayName("Test searchByUsername()")
	public void testsearchByUsernameValid() throws Exception {
		when(userRepo.findByUsernameContaining("john")).thenReturn(List.of(user1));
		assertThat(userService.searchByUsername("john")).hasSize(1);
		verify(userRepo).findByUsernameContaining(any());
	}
	
	@Test
	@DisplayName("Test searchByUsername() with empty users")
	public void testSearchByUsernameEmptyInvalid() throws Exception {
		when(userRepo.findByUsernameContaining("john")).thenReturn(List.of());
		assertThrows(NoSuchUserException.class,() -> { userService.searchByUsername("john"); });
		verify(userRepo).findByUsernameContaining(any());
	}
	
	/************************************************  removeUser()  ********************************************************/
	
	@Test
	@DisplayName("Test removeUser()")
	public void testRemoveUserValid() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.of(user1));
		assertThat(userService.removeUser("john").getMessage()).isEqualTo("User Deleted Successfully");
		verify(userRepo).findById(any());
	}
	
	@Test
	@DisplayName("Test removeUser() with invalid username")
	public void testRemoveUserInvalidUsername() throws Exception {
		when(userRepo.findById("john")).thenReturn(Optional.empty());
		assertThrows(NoSuchUserException.class,() -> { userService.removeUser("john"); });
		verify(userRepo).findById(any());
	}
	
	
}
