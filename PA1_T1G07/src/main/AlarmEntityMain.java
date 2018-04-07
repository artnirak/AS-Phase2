package main;

import alarmentity.AlarmEntitySPEEDConsumer;
import gui.AlarmEntityUI;
import interfaces.Constantes;
import interfaces.ConsumerInterface;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class AlarmEntityMain implements Constantes {
    public static void main(String[] args) {
        AlarmEntityUI aeui = new AlarmEntityUI();
        
        ConsumerInterface speedconsumer_1 = new AlarmEntitySPEEDConsumer(aeui,4);
        ConsumerInterface speedconsumer_2 = new AlarmEntitySPEEDConsumer(aeui,5);
        ConsumerInterface speedconsumer_3 = new AlarmEntitySPEEDConsumer(aeui,6);
        
        new Thread(() -> speedconsumer_1.consumeData()).start();
        new Thread(() -> speedconsumer_2.consumeData()).start();
        new Thread(() -> speedconsumer_3.consumeData()).start();
    }

}
