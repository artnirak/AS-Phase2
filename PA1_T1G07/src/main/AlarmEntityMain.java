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
        
        ConsumerInterface speedconsumer_1 = new AlarmEntitySPEEDConsumer(aeui);
        
        new Thread(() -> speedconsumer_1.consumeData()).start();
    }

}
