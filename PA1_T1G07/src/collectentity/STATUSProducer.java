package collectentity;

import callback.ProducerCallback;
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
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author Francisco Lopes 76406
 */
public class STATUSProducer implements ProducerInterface {

    private final CollectEntityUI ceui;
    
    public STATUSProducer(CollectEntityUI ceui) {
        this.ceui = ceui;
    }

    public void produceData(String data) {
        String topicName = "EnrichTopic_3";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "all"); //LEADER PEDE CONFIRMAÇÃO A TODAS AS ISR

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, data);
            producer.send(record, new ProducerCallback());
        }
    }

    public void processData(String filename) {
        File file = new File(Paths.get(System.getProperty("user.dir"), "src", "data", filename).toString());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            while ((st = br.readLine()) != null) {
                //produceData(st);
                ceui.appendText(st);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(STATUSProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}