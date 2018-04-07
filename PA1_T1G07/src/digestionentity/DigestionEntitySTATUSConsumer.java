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

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class DigestionEntitySTATUSConsumer implements Constantes, ConsumerInterface {
    

    private final static String TOPIC = "EnrichTopic_3";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    private final DigestionEntityUI deui;
    private final ProducerInterface producer;
    private int id;
    
    public DigestionEntitySTATUSConsumer(DigestionEntityUI deui, ProducerInterface producer,int id) {
        this.deui = deui;
        this.producer = producer;
        this.id=id;
    }
    
    public Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,STATUS_DIGESTION_CONSUMER_GROUP);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "500");  //in case of rebalance, reprocessing can happen!

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
                        = consumer.poll(100);

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
                    String partition = Integer.toString(record.partition());
                    System.out.println("-----------------------------------digestion"+this.id + " - " + record.topic() +" - " + partition);
                    deui.appendReceived(data);
                    producer.produceData(enrichData(data));
                });

            }
        }
        System.out.println("DONE");
    }
}
