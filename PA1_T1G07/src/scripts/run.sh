
#/home/pedro/Desktop/MEI/AS/KAFKA/kafka_2.11-1.0.0  

echo Input the path to kafka
read pwd_kafka
kafka_home=${pwd_kafka}


xterm -T "zookeeper" -hold -e "cd ${kafka_home};bin/zookeeper-server-start.sh config/zookeeper.properties" &

xterm -T "kafkaserver" -hold -e "cd ${kafka_home}; bin/kafka-server-start.sh config/server.properties" &

#FAZER O COPY AUTOMATICAMENTE DAS PROPERTIES DOS NODES? OU PÔR NO README???

xterm -T "node 1" -hold -e "cd ${kafka_home}; bin/kafka-server-start.sh config/server-1.properties" &
xterm -T "node 2" -hold -e "cd ${kafka_home}; bin/kafka-server-start.sh config/server-2.properties" &
xterm -T "node 3" -hold -e "cd ${kafka_home}; bin/kafka-server-start.sh config/server-3.properties" &

#INTERPRETAR A PARTE 6.1 DA FICHA, VER PARTITIONING MODE, REPLICATION E NR DE TOPICOS
#construir topicos 1 -> HB.TXT  2 -> SPEED.TXT 3 -> STATUS.TXT

sleep 5

xterm -T "general" -hold -e "cd ${kafka_home};bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic EnrichTopic_1;bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 4 --partitions 4 --topic EnrichTopic_2;bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 4 --partitions 4 --topic EnrichTopic_3 " &


#xterm -T "general" -hold -e "cd ${kafka_home};bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic EnrichedTopic_1;bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 4 --partitions 1 --topic EnrichedTopic_2;bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 4 --partitions 1 --topic EnrichedTopic_3  " &

