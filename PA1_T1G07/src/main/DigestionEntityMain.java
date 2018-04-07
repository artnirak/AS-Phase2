package main;

import digestionentity.*;
import gui.DigestionEntityUI;
import interfaces.ConsumerInterface;
import interfaces.ProducerInterface;

/**
 *
 * @author Francisco Lopes 76406
 */
public class DigestionEntityMain {
    public static void main(String[] args) {
        DigestionEntityUI deui = new DigestionEntityUI();
        
        ProducerInterface hbproducer = new DigestionEntityHBProducer(deui);
        ConsumerInterface hbconsumer_1 = new DigestionEntityHBConsumer(deui, hbproducer,1);
        
        ProducerInterface speedproducer = new DigestionEntitySPEEDProducer(deui);
        ConsumerInterface speedconsumer_1 = new DigestionEntitySPEEDConsumer(deui, speedproducer,4);
        ConsumerInterface speedconsumer_2 = new DigestionEntitySPEEDConsumer(deui, speedproducer,5);
        ConsumerInterface speedconsumer_3 = new DigestionEntitySPEEDConsumer(deui, speedproducer,6);
        
        
        ProducerInterface statusproducer = new DigestionEntitySTATUSProducer(deui);
        ConsumerInterface statusconsumer_1 = new DigestionEntitySTATUSConsumer(deui, statusproducer,7);
        ConsumerInterface statusconsumer_2 = new DigestionEntitySTATUSConsumer(deui, statusproducer,8);
        ConsumerInterface statusconsumer_3 = new DigestionEntitySTATUSConsumer(deui, statusproducer,9);
        
        
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
