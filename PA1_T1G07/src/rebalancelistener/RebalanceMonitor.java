package rebalancelistener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

/**
 *
 * @author pedro
 */
public class RebalanceMonitor implements ConsumerRebalanceListener {
    
    private final KafkaConsumer consumer;
    private final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap();
    private final String entity;
    
    public RebalanceMonitor(KafkaConsumer consumer, String entity)
    {
        this.consumer = consumer;
        this.entity=entity;
    }
    
    public void addOffset(String topic, int partition, long offset)
    {
        currentOffsets.put(new TopicPartition(topic, partition), new OffsetAndMetadata(offset, "commit"));
    }
    
    public Map<TopicPartition, OffsetAndMetadata> getCurrentOffsets()
    {
        return currentOffsets;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        System.out.println("Revoked partitions:");
        partitions.forEach((part) -> {
            System.out.println(this.entity+ "- "+part.partition()+"; ");
        });
        
        System.out.println("Comitted partitions:");
        currentOffsets.keySet().forEach((tp) -> {
            System.out.println(this.entity+ "- " +tp.partition()+"; ");
        });
        
        consumer.commitSync(currentOffsets);
        currentOffsets.clear();
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println("Assigned partitions:");
        partitions.forEach((part) -> {
            System.out.println(this.entity+ "- " +part.partition()+"; ");
        });
    }
    
}
