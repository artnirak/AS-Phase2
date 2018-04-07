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
    private final Producer producer;

    public DigestionEntityHBProducer(DigestionEntityUI deui) {
        this.deui = deui;
        props = new Properties();

        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    @Override
    public void produceData(String data) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, data);
        producer.send(record);
        deui.appendSent(data);
    }

    @Override
    public void closeProducer() {
        this.producer.close();
    }
}
