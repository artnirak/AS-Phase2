/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    private KafkaConsumer consumer;
    private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap();
    
    public RebalanceMonitor(KafkaConsumer consumer)
    {
        this.consumer = consumer;
    }
    
    public void addOffset(String topic, int partition, long offset)
    {
        currentOffsets.put(new TopicPartition(topic, partition), new OffsetAndMetadata(offset, "commit"));
    }
    
    public Map<TopicPartition, OffsetAndMetadata> getCurrentOffsets()
    {
        return currentOffsets;
    }

    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        System.out.println("Revoked partitions:");
        for(TopicPartition part: partitions)
        {
            System.out.println(part.partition()+"; ");
        }
        
        System.out.println("Comitted partitions:");
        for(TopicPartition tp: currentOffsets.keySet())
        {
            System.out.println(tp.partition()+"; ");
        }
        
        consumer.commitSync(currentOffsets);
        currentOffsets.clear();
    }

    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println("Assigned partitions:");
        for(TopicPartition part: partitions)
        {
            System.out.println(part.partition()+"; ");
        }
    }
    
}
