# TweetAppBackend
Tweet Application Backend with springboot,jwt,junit,kafka,prometheus,mongodb,elk,swagger

1.Run zookeeper->
C:\kafka_2.12-2.4.1\bin\windows\zookeeper-server-start.bat C:\kafka_2.12-2.4.1\config\zookeeper.properties

2.Run kafka server->
C:\kafka_2.12-2.4.1\bin\windows\kafka-server-start.bat C:\kafka_2.12-2.4.1\config\server.properties

3.Only first time to create kafka topic->
C:\kafka_2.12-2.4.1\bin\windows\kafka-topics.bat -zookeeper localhost:2181 -topic TweetMessage --create --partitions 3 --replication-factor 1

4.To list kafka topics->
cd C:\kafka_2.12-2.4.1\bin\windows\
kafka-topics.bat --list --zookeeper localhost:2181
To get consumer messages->
kafka-console-consumer -bootstrap-server localhost:9092 -topic TweetMessage

5.Start Application in sts(8888)
  Application swagger
  localhost:8888/swagger-ui/

6.Check actuator
localhost:8888/actuator

7.Run prometheus in cmd(prometheus)
localhost:9090

8.Run grafana in cmd(grafana-server)
localhost:3000
cred's: admin/admin

9.Run elasticsearch in cmd(elasticsearch)
localhost:9200

10.Run logstash in cmd in logstash/bin/
logstash -f logstash.conf
localhost:9200/_cat/indices
localhost:9600

11.Run kibana in cmd in kibana/bin/
localhost:5601
