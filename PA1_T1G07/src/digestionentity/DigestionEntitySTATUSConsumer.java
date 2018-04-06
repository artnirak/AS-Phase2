/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digestionentity;

//import batchentity.BatchEntity;
import interfaces.Constantes;
import interfaces.ConsumerInterface;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

/**
 *
 * @author pedro
 */
public class DigestionEntitySTATUSConsumer implements Constantes, ConsumerInterface {
    

    private final static String TOPIC = "EnrichTopic_3";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public Consumer<String, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,STATUS_DIGESTION_CONSUMER_GROUP);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "2000");  //in case of rebalance, reprocessing can happen!

        // Create the consumer using props.
        final Consumer<String, String> consumer
                = new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

    public void consumeData() {
        try (Consumer<String, String> consumer = createConsumer()) {
            final int giveUp = 100;
            int noRecordsCount = 0;

            while (true) {
                final ConsumerRecords<String, String> consumerRecords
                        = consumer.poll(1000);

                if (consumerRecords.count() == 0) {
                    noRecordsCount++;
                    if (noRecordsCount > giveUp) {
                        break;
                    } else {
                        continue;
                    }
                }
                
                consumerRecords.forEach(record -> {
                    String enrichedData = enrichData(record.value());
                });

            }
        }
        System.out.println("DONE");
    }

    public String enrichData(String data) {
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
    /*
    //TESTING
    private void processData(String filename) {
        File file = new File(Paths.get(System.getProperty("user.dir"), "src", "data", filename).toString());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st, enrichedData;
            while ((st = br.readLine()) != null) {
                deui.appendReceived(st);
                enrichedData = enrichData(st);
                deui.appendSent(enrichedData);
                //TESTING
                //be.storeData(enrichedData);
            }   
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(DigestionEntityHBConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/

}

//criar consumer que pega nos dados enviados pelo collect e processa-os "enrichment", o que é o car_reg ?
    
    //criar producer que vai enviar os dados processados para os consumers das outras entities, classe separada?  
