package batchentity;

import static batchentity.StoreData.storeData;
import gui.BatchEntityUI;
import interfaces.Constantes;
import interfaces.ConsumerInterface;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

/**
 *
 * @author pedro
 */
public class BatchEntitySTATUSConsumer implements Constantes, ConsumerInterface {
    

    private final static String TOPIC = "EnrichedTopic_3";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    private final BatchEntityUI beui;
    private int id;
    
    public BatchEntitySTATUSConsumer(BatchEntityUI beui, int id) {
        this.beui = beui;
        this.id=id;
    }
    
    public Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,STATUS_BATCH_CONSUMER_GROUP);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "2000");  //in case of rebalance, reprocessing can happen!

        // Create the consumer using props.
        final Consumer<String, String> consumer
                = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

    @Override
    public void consumeData() {
        try (Consumer<String, String> consumer = createConsumer()) {
            final int giveUp = 100;
            int noRecordsCount = 0;

            while (true) {
                final ConsumerRecords<String, String> consumerRecords
                        = consumer.poll(1000);

                if (consumerRecords.count() == 0) {
                    noRecordsCount++;
                    if (noRecordsCount > giveUp) {
                        break;
                    } else {
                        continue;
                    }
                }
                
                consumerRecords.forEach(record -> {
                    String data = record.value();
                    beui.appendText(data);
                    storeData(data);
                });

            }
        }
        System.out.println("DONE");
    }
}
