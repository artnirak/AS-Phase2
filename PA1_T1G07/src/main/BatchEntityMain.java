package main;

import batchentity.BatchEntityHBConsumer;
import batchentity.BatchEntitySPEEDConsumer;
import batchentity.BatchEntitySTATUSConsumer;
import gui.BatchEntityUI;
import interfaces.ConsumerInterface;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class BatchEntityMain {
    public static void main(String[] args) {
        BatchEntityUI beui = new BatchEntityUI();
        
        ConsumerInterface hbconsumer_1 = new BatchEntityHBConsumer(beui);
        ConsumerInterface hbconsumer_2 = new BatchEntityHBConsumer(beui);
        ConsumerInterface hbconsumer_3 = new BatchEntityHBConsumer(beui);
        
        ConsumerInterface speedconsumer_1 = new BatchEntitySPEEDConsumer(beui);
        
        ConsumerInterface statusconsumer_1 = new BatchEntitySTATUSConsumer(beui);
        
        //parallel consumption each consumer is atributed a partition
        new Thread(() -> hbconsumer_1.consumeData()).start();
        new Thread(() -> hbconsumer_2.consumeData()).start();
        new Thread(() -> hbconsumer_3.consumeData()).start();
        
        new Thread(() -> speedconsumer_1.consumeData()).start();
        
        new Thread(() -> statusconsumer_1.consumeData()).start();
        
    }
}
