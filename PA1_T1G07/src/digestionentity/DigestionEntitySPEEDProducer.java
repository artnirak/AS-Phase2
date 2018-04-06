package digestionentity;

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
 */
public class DigestionEntitySPEEDProducer implements ProducerInterface, Constantes {

    private final DigestionEntityUI deui;
    private static final String TOPIC_NAME = "EnrichedTopic_2";
    private final Properties props;
    
    public DigestionEntitySPEEDProducer(DigestionEntityUI deui) {
        this.deui = deui;
        props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "all"); //confirmation from all ISRs
        
        /*The Producer config property retries defaults to 0 and is the retry count if Producer does not get an ack from Kafka Broker. 
            The Producer will only retry if record send fail is deemed a transient error (API). The Producer will act as if your producer code resent the record on a failed attempt. 
            Note that timeouts are re-tried, but retry.backoff.ms (default to 100 ms) is used to wait after failure before retrying the request again. 
            If you set retry > 0, then you should also set max.in.flight.requests.per.connection to 1, or there is the possibility that a re-tried message could be delivered out of order. 
            You have to decide if out of order message delivery matters for your application. */
        
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,1); //ORDER IS IMPORTANT
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        //Request timeout - request.timeout.ms
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);

        //Only retry after one second.
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);
    }

    @Override
    public void produceData(String data) {        
        Producer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, data);

        try {
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Metadata: "+ metadata.partition() + "; "+ metadata.offset());
            System.out.println("Sent successfuly.");
            deui.appendSent(data);
            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(DigestionEntitySPEEDProducer.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}