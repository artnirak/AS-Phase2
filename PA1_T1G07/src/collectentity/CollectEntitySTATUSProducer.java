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
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 *
 * @author Francisco Lopes 76406
 */
public class CollectEntitySTATUSProducer implements ProducerInterface, Constantes {

    private final CollectEntityUI ceui;
    private final Properties props;
    private final String topicName = "EnrichTopic_3";
    private final KafkaProducer producer;

    public CollectEntitySTATUSProducer(CollectEntityUI ceui) {
        this.ceui = ceui;
        this.props = new Properties();
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

        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, data);

        try {
            RecordMetadata metadata = (RecordMetadata) this.producer.send(record).get();
            System.out.println("Metadata: " + metadata.partition() + "; " + metadata.offset() + "; " + metadata.timestamp());
            System.out.println("Sent successfuly.");

        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(CollectEntitySPEEDProducer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void processData() {
        File file = new File(Paths.get(DATA_PATH, STATUS_FILE).toString());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            while ((st = br.readLine()) != null) {
                produceData(st);
                ceui.appendText(st);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + STATUS_FILE + " not found.");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(CollectEntitySTATUSProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void closeProducer() {
        this.producer.close();
    }
}
