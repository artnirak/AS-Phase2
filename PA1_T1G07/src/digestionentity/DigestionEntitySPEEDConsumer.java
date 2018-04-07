package digestionentity;

import static digestionentity.DataEnrichment.enrichData;
import gui.DigestionEntityUI;
import interfaces.Constantes;
import interfaces.ConsumerInterface;
import interfaces.ProducerInterface;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import rebalancelistener.RebalanceMonitor;

/**
 *
 * @author pedro
 */
public class DigestionEntitySPEEDConsumer implements Constantes, ConsumerInterface {
    
    private final static String TOPIC = "EnrichTopic_2";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    private final DigestionEntityUI deui;
    private final ProducerInterface producer;
    private int id;
    
    public DigestionEntitySPEEDConsumer(DigestionEntityUI deui, ProducerInterface producer, int id) {
        this.deui = deui;
        this.producer = producer;
        this.id = id;
    }
    
    private Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,SPEED_DIGESTION_CONSUMER_GROUP);
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
            RebalanceMonitor rebmon = new RebalanceMonitor((KafkaConsumer) consumer);
        
            // Subscribe to the topic.
            consumer.subscribe(Collections.singletonList(TOPIC), rebmon);
            
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
                    rebmon.addOffset(record.topic(), record.partition(), record.offset());
                    String partition = Integer.toString(record.partition());
                    System.out.println("-----------------------------------digestion"+this.id + " - " + record.topic() +" - " + partition);
                    deui.appendReceived(data);
                    producer.produceData(enrichData(data));
                });
                consumer.commitSync(rebmon.getCurrentOffsets());
            }
        }
    }
}
