package digestionentity;

import gui.DigestionEntityUI;
import interfaces.Constantes;
import interfaces.ProducerInterface;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author Francisco Lopes 76406
 */
public class DigestionEntityHBProducer implements ProducerInterface, Constantes {

    private final DigestionEntityUI deui;
    private final String TOPIC_NAME = "EnrichedTopic_1";
    private final Properties props;
    
    public DigestionEntityHBProducer(DigestionEntityUI deui) {
        this.deui = deui;
        props = new Properties();
        
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    @Override
    public void produceData(String  data) {

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, data);
            producer.send(record);
            deui.appendSent(data);            
        } 
    }
}