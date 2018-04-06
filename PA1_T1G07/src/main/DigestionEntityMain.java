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
        ConsumerInterface hbconsumer = new DigestionEntityHBConsumer(deui);
        ProducerInterface speedproducer = new DigestionEntitySPEEDProducer(deui);
        ConsumerInterface speedconsumer = new DigestionEntitySPEEDConsumer(deui);
        ProducerInterface statusproducer = new DigestionEntitySTATUSProducer(deui);
        ConsumerInterface statusconsumer = new DigestionEntitySTATUSConsumer(deui);
        
        hbconsumer.consumeData(hbproducer);
        speedconsumer.consumeData(speedproducer);
        statusconsumer.consumeData(statusproducer);
        
    }
}
