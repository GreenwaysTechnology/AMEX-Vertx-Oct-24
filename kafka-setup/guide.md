docker images
REPOSITORY               TAG       IMAGE ID       CREATED         SIZE
wurstmeister/kafka       latest    a692873757c0   17 months ago   468MB
wurstmeister/zookeeper   latest    3f43f72cb283   4 years ago     510MB

docker ps
CONTAINER ID   IMAGE                    COMMAND                  CREATED         STATUS         PORTS                                                NAMES
a517d4779339   wurstmeister/zookeeper   "/bin/sh -c '/usr/sb…"   2 minutes ago   Up 2 minutes   22/tcp, 2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp   zookeeper
e12facffd187   wurstmeister/kafka       "start-kafka.sh"         2 minutes ago   Up 2 minutes   0.0.0.0:9092->9092/tcp                               kafka

display only container names:

docker ps --format "table {{.Names}}"
NAMES
zookeeper
kafka

Inside container 
# cd opt
# ls -l
total 12
lrwxrwxrwx 1 root root   21 May 28  2022 kafka -> /opt/kafka_2.13-2.8.1
drwxr-xr-x 1 root root 4096 Nov 19 04:55 C
drwxr-xr-x 2 root root 4096 May 28  2022 overrides

cd kafka_2.13-2.8.1
ls
LICENSE  NOTICE  bin  config  libs  licenses  logs  site-docs

./bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic test
Created topic test.
# ./bin/kafka-topics.sh --bootstrap-server kafka:9092 --describe --topic test
Topic: test     TopicId: AefYVLysQoSSPA1rgElYIQ PartitionCount: 1       ReplicationFactor: 1    Configs: segment.bytes=1073741824
        Topic: test     Partition: 0    Leader: 1001    Replicas: 1001  Isr: 1001
#
........................................................................................                            Confluent Kafka - Zookeeper
........................................................................................
docker-compose.yml
version: '2'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    hostname: zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  broker:
    image: confluentinc/cp-server:7.5.0
    hostname: broker
    container_name: broker
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "9101:9101"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://broker:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_METRIC_REPORTERS: io.confluent.metrics.reporter.ConfluentMetricsReporter
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS: 0
      KAFKA_CONFLUENT_LICENSE_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_CONFLUENT_BALANCER_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_JMX_PORT: 9101
      KAFKA_JMX_HOSTNAME: localhost
      CONFLUENT_METRICS_REPORTER_BOOTSTRAP_SERVERS: broker:29092
      CONFLUENT_METRICS_REPORTER_TOPIC_REPLICAS: 1
      CONFLUENT_METRICS_ENABLE: 'true'
      CONFLUENT_SUPPORT_CUSTOMER_ID: 'anonymous'

    
docker-compose.yml up

docker images
REPOSITORY                  TAG       IMAGE ID       CREATED         SIZE
confluentinc/cp-server      7.5.0     0b63491dd65a   3 months ago    1.76GB
confluentinc/cp-zookeeper   7.5.0     04bd40128a4e   3 months ago    849MB

docker ps --format "table {{.Names}}"
NAMES
broker
zookeeper

How to run kafka commands?

docker exec -it broker sh
sh-4.4$ kafka-topics --bootstrap-server localhost:9092 --create --topic todos
Created topic todos.
 kafka-console-producer --bootstrap-server localhost:9092 --topic todos
>Learn Kafka
>Learn kafka streams

........................................................................................
                                Kafka with KRaft
                                