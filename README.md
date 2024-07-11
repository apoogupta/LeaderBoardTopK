

##COMMANDS


1) ./bin/zookeeper-server-start.sh config/zookeeper.properties

   this command starts the zookeeper server with the properties defined in zookeeper.properties file

2. ./bin/kafka-server-start.sh config/server.properties

   creates a kafka broker
   Message Storage and Retrieval:
* Storage: Kafka brokers store messages in topics. Each topic is split into partitions, and the broker stores these partitions on disk.
* Retrieval: When a consumer requests messages from a topic, the broker retrieves the messages from the appropriate partitions and delivers them to the consumer.

3. ./bin/kafka-topics.sh --create --topic playersScores --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

    creates a topic and does one partition with no replication


to start redis server - brew services start redis

###MySql Config

- mysql -u root -p password - apoorva123
- mysql> create database taskmanager;



