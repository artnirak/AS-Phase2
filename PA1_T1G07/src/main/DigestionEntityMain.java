package main;

import digestionentity.*;
import gui.DigestionEntityUI;
import interfaces.ConsumerInterface;
import interfaces.ProducerInterface;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class DigestionEntityMain {
    public static void main(String[] args) {
        DigestionEntityUI deui = new DigestionEntityUI();
        
        ProducerInterface hbproducer = new DigestionEntityHBProducer(deui);
        ConsumerInterface hbconsumer_1 = new DigestionEntityHBConsumer(deui, hbproducer);
        
        ProducerInterface speedproducer = new DigestionEntitySPEEDProducer(deui);
        ConsumerInterface speedconsumer_1 = new DigestionEntitySPEEDConsumer(deui, speedproducer);
        ConsumerInterface speedconsumer_2 = new DigestionEntitySPEEDConsumer(deui, speedproducer);
        ConsumerInterface speedconsumer_3 = new DigestionEntitySPEEDConsumer(deui, speedproducer);
        
        
        ProducerInterface statusproducer = new DigestionEntitySTATUSProducer(deui);
        ConsumerInterface statusconsumer_1 = new DigestionEntitySTATUSConsumer(deui, statusproducer);
        ConsumerInterface statusconsumer_2 = new DigestionEntitySTATUSConsumer(deui, statusproducer);
        ConsumerInterface statusconsumer_3 = new DigestionEntitySTATUSConsumer(deui, statusproducer);
        
        
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
