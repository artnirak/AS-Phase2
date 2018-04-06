package collectentity;

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
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * @author Francisco Lopes 76406
 */
public class CollectEntityHBProducer implements ProducerInterface, Constantes {

    private final CollectEntityUI ceui;
    private final Properties props;
    private final String topicName = "EnrichTopic_1";
    private final KafkaProducer producer;
    
    public CollectEntityHBProducer(CollectEntityUI ceui) {
        this.ceui = ceui;
        this.props = new Properties();
        
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    @Override
    public void produceData(String data) {  
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, data);
        this.producer.send(record);
    }

    public void processData() {
        File file = new File(Paths.get(DATA_PATH, HB_FILE).toString());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            while ((st = br.readLine()) != null) {
                produceData(st);
                ceui.appendText(st);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + HB_FILE + " not found.");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(CollectEntityHBProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void closeProducer() {
        this.producer.close();
    }

}