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
import rebalancelistener.RebalanceMonitor;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class BatchEntitySPEEDConsumer implements Constantes, ConsumerInterface {
    
    private final static String TOPIC = "EnrichedTopic_2";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    private final BatchEntityUI beui;
    
    public BatchEntitySPEEDConsumer(BatchEntityUI beui) {
        this.beui = beui;
    }
    
    private Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,SPEED_BATCH_CONSUMER_GROUP);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put("enable.auto.commit", "false");
        


        // Create the consumer using props.
        final KafkaConsumer<String, String> consumer
                = new KafkaConsumer<>(props);
        
        return consumer;
    }

    @Override
    public void consumeData() {
        try (Consumer<String, String> consumer = createConsumer()) {
            // Create the rebalance Listener
            RebalanceMonitor rebmon = new RebalanceMonitor((KafkaConsumer) consumer, "batch");
        
            // Subscribe to the topic.
            consumer.subscribe(Collections.singletonList(TOPIC), rebmon);
            

            while (true) {
                final ConsumerRecords<String, String> consumerRecords
                        = consumer.poll(1000);
                
                consumerRecords.forEach(record -> {
                    String data = record.value();
                    rebmon.addOffset(record.topic(), record.partition(), record.offset());
                    String partition = Integer.toString(record.partition());
                    beui.appendText(data);
                    storeData(data);
                });
                consumer.commitSync(rebmon.getCurrentOffsets());
            }
        }
    }
}
