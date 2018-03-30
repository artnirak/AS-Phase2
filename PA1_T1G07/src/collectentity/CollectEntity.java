package collectentity;

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
public class CollectEntity {

    public static void main() {
        processData("HB.txt");
        processData("SPEED.txt");
        processData("STATUS.txt");
    }

    private static void sendData(String data) {
        String topicName = "SimpleProducerTopic";
        String key = "Key1";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(topicName, key, data);
            producer.send(record);
        }
    }

    private static void processData(String filename) {
        File file = new File(Paths.get(System.getProperty("user.dir"), "src", "data", filename).toString());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            while ((st = br.readLine()) != null) {
                //sendData(st);
                System.out.println(st);
            }   
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(CollectEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
