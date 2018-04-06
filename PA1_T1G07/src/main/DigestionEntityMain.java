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
        ConsumerInterface hbconsumer = new DigestionEntityHBConsumer(deui, hbproducer);
        ProducerInterface speedproducer = new DigestionEntitySPEEDProducer(deui);
        ConsumerInterface speedconsumer = new DigestionEntitySPEEDConsumer(deui, speedproducer);
        ProducerInterface statusproducer = new DigestionEntitySTATUSProducer(deui);
        ConsumerInterface statusconsumer = new DigestionEntitySTATUSConsumer(deui, statusproducer);
        
        hbconsumer.consumeData();
        speedconsumer.consumeData();
        statusconsumer.consumeData();
        
    }
}
