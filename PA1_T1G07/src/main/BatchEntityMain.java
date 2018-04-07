package main;

import batchentity.BatchEntityHBConsumer;
import batchentity.BatchEntitySPEEDConsumer;
import batchentity.BatchEntitySTATUSConsumer;
import gui.BatchEntityUI;
import interfaces.ConsumerInterface;

/**
 *
 * @author Francisco Lopes 76406
 */
public class BatchEntityMain {
    public static void main(String[] args) {
        BatchEntityUI beui = new BatchEntityUI();
        ConsumerInterface hbconsumer_1 = new BatchEntityHBConsumer(beui,1);
        
        ConsumerInterface speedconsumer_1 = new BatchEntitySPEEDConsumer(beui,4);
        ConsumerInterface speedconsumer_2 = new BatchEntitySPEEDConsumer(beui,5);
        ConsumerInterface speedconsumer_3 = new BatchEntitySPEEDConsumer(beui,6);
        
        ConsumerInterface statusconsumer_1 = new BatchEntitySTATUSConsumer(beui,7);
        ConsumerInterface statusconsumer_2 = new BatchEntitySTATUSConsumer(beui,8);
        ConsumerInterface statusconsumer_3 = new BatchEntitySTATUSConsumer(beui,9);
        
        //parallel consumption each consumer is atributed a partition
        new Thread(() -> hbconsumer_1.consumeData()).start();
        
        new Thread(() -> speedconsumer_1.consumeData()).start();
        new Thread(() -> speedconsumer_2.consumeData()).start();
        new Thread(() -> speedconsumer_3.consumeData()).start();
        
        new Thread(() -> statusconsumer_1.consumeData()).start();
        new Thread(() -> statusconsumer_2.consumeData()).start();
        new Thread(() -> statusconsumer_3.consumeData()).start();
        
    }
}
