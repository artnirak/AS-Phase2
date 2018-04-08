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

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class ReportEntityHBConsumer implements Constantes, ConsumerInterface {
    
    private final static String TOPIC = "EnrichedTopic_1";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    private final ReportEntityUI reui;
    
    private final ReportData rd;
    
    public ReportEntityHBConsumer(ReportEntityUI reui, ReportData rd) {
        this.reui = reui;
        this.rd=rd;
    }
    
    private Consumer<String,String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,HB_REPORT_CONSUMER_GROUP);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<String,String> consumer;
        consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }
    
    @Override
    public void consumeData() {
        try (Consumer<String, String> consumer = createConsumer()) {

            while (true) {
                final ConsumerRecords<String, String> consumerRecords
                        = consumer.poll(1000);
                
                consumerRecords.forEach(record -> {
                    String data = record.value();
                    reui.appendText(data);
                    rd.updateReport(data);
                });

            }
        }
    }

}
