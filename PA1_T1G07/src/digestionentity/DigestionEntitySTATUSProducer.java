package digestionentity;

import collectentity.*;
import gui.DigestionEntityUI;
import interfaces.Constantes;
import interfaces.ProducerInterface;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class DigestionEntitySTATUSProducer implements ProducerInterface, Constantes {

    private final DigestionEntityUI deui;
    private static final String TOPIC_NAME = "EnrichedTopic_3";
    private final Properties props;
    private final Producer producer;

    public DigestionEntitySTATUSProducer(DigestionEntityUI deui) {
        this.deui = deui;
        props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "all"); //LEADER PEDE CONFIRMAÇÃO A TODAS AS ISR

        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1); //ORDER IS IMPORTANT
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        //Request timeout - request.timeout.ms
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);

        //Only retry after one second.
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);
        
        this.producer = new KafkaProducer<>(props);
    }

    @Override
    public void produceData(String data) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, data);

        try {
            RecordMetadata metadata = (RecordMetadata) this.producer.send(record).get();
            System.out.println("Metadata: " + metadata.partition() + "; " + metadata.offset());
            System.out.println("Sent successfuly.");
            deui.appendSent(data);

        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(CollectEntitySPEEDProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void closeProducer() {
        this.producer.close();
    }
}
