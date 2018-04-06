package digestionentity;

import collectentity.*;
import gui.CollectEntityUI;
import callback.ProducerCallback;
import interfaces.Constantes;
import interfaces.ProducerInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
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
public class DigestionEntitySTATUSProducer implements ProducerInterface, Constantes {

    private final CollectEntityUI deui;
    
    public DigestionEntitySTATUSProducer(CollectEntityUI deui) {
        this.deui = deui;
    }

    public void produceData(String data) {
        String topicName = "EnrichedTopic_3";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "all"); //LEADER PEDE CONFIRMAÇÃO A TODAS AS ISR
        
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,1); //ORDER IS IMPORTANT
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        
        //Request timeout - request.timeout.ms
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);

        //Only retry after one second.
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);
        
        Producer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, data);

        try {
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Metadata: "+ metadata.partition() + "; "+ metadata.offset());
            System.out.println("Sent successfuly.");
            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(CollectEntitySPEEDProducer.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    public void processData(StringBuilder sb_data) {
        String[] lines = sb_data.toString().split("\\n");
        
        for(String line: lines)
        {
            //produceData(line);
            deui.appendText(line);
        }
    }
}