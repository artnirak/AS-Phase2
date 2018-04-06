package digestionentity;

import collectentity.*;
import callback.ProducerCallback;
import gui.CollectEntityUI;
import interfaces.Constantes;
import interfaces.ProducerInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author Francisco Lopes 76406
 */
public class DigestionEntityHBProducer implements ProducerInterface, Constantes {

    private final CollectEntityUI deui;
    
    public DigestionEntityHBProducer(CollectEntityUI deui) {
        this.deui = deui;
    }

    public void produceData(String  data) {
        String topicName = "EnrichedTopic_1";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, data);
            producer.send(record);
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