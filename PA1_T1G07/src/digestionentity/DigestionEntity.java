/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digestionentity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class DigestionEntity {
    
    private DigestionEntityUI deui;

    public void main() {
        //consumeData();
        deui = new DigestionEntityUI();
        processData("HB.txt");
        processData("SPEED.txt");
        processData("STATUS.txt");

    }

    private final static String TOPIC = "test";
    private final static String BOOTSTRAP_SERVERS
            = "localhost:9092,localhost:9093,localhost:9094";

    private static Consumer<Long, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "KafkaExampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());

        // Create the consumer using props.
        final Consumer<Long, String> consumer
                = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

    static void consumeData() {
        try (Consumer<Long, String> consumer = createConsumer()) {
            final int giveUp = 100;
            int noRecordsCount = 0;

            while (true) {
                final ConsumerRecords<Long, String> consumerRecords
                        = consumer.poll(1000);

                if (consumerRecords.count() == 0) {
                    noRecordsCount++;
                    if (noRecordsCount > giveUp) {
                        break;
                    } else {
                        continue;
                    }
                }
                //FAZER O ENRICHMENT ALGURES POR AQUI -> ENRICHMENT()
                consumerRecords.forEach(record -> {
                    System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                            record.key(), record.value(),
                            record.partition(), record.offset());
                    String enrichedData = enrichData(record.value());
                });

                consumer.commitAsync();
            }
        }
        System.out.println("DONE");
    }

    private static String enrichData(String data) {
        String[] s;
        StringBuilder sb = new StringBuilder();
        s = data.split(" ");
        for (String parameter : s) {
            if (parameter.matches("00|01|02"))
                sb.append("XX-YY-").append(String.format("%02d", Integer.parseInt(s[0]))).append(" ");
            sb.append(parameter).append(" ");
        }
        if (s[2].equals("01"))
            sb.append("100");
        return sb.toString().trim();
    }
    
    //TESTING
    private void processData(String filename) {
        File file = new File(Paths.get(System.getProperty("user.dir"), "src", "data", filename).toString());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            while ((st = br.readLine()) != null) {
                deui.appendReceived(st);
                deui.appendSent(enrichData(st));
            }   
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(DigestionEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//criar consumer que pega nos dados enviados pelo collect e processa-os "enrichment", o que é o car_reg ?
    
    //criar producer que vai enviar os dados processados para os consumers das outras entities, classe separada?  
