package callback;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 *
 * @author pedro
 */
public class ProducerCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata rm, Exception excptn) {
        if(excptn != null)
            System.out.println("Async Producer failed with an exception!");
        else 
            System.out.println("Async Producer call success!");
    }
    
    
}
