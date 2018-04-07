package reportentity;

import gui.ReportEntityUI;
import interfaces.Constantes;
import interfaces.ConsumerInterface;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import rebalancelistener.RebalanceMonitor;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class ReportEntitySPEEDConsumer implements Constantes, ConsumerInterface {
    
    private final static String TOPIC = "EnrichedTopic_2";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    private final ReportEntityUI reui;
    private int id;
    
    private final ReportData rd;
    
    public ReportEntitySPEEDConsumer(ReportEntityUI reui, ReportData rd, int id) {
        this.reui=reui;
        this.id=id;
        this.rd=rd;
    }
    
    private Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,SPEED_REPORT_CONSUMER_GROUP);
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
            RebalanceMonitor rebmon = new RebalanceMonitor((KafkaConsumer) consumer, "report");
        
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
                    System.out.println("-----------------------------------report"+this.id + " - " + record.topic() +" - " + partition);
                    reui.appendText(data);
                    rd.updateReport(data);
                });
                consumer.commitSync(rebmon.getCurrentOffsets());
            }
        }
    }
    
}
