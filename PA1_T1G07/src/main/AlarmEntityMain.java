package main;

import alarmentity.AlarmEntitySPEEDConsumer;
import collectentity.CollectEntityHBProducer;
import gui.AlarmEntityUI;
import interfaces.Constantes;
import interfaces.ConsumerInterface;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Lopes 76406
 */
public class AlarmEntityMain implements Constantes {
    public static void main(String[] args) {
        AlarmEntityUI aeui = new AlarmEntityUI();
        //TESTING
        ConsumerInterface speedconsumer_1 = new AlarmEntitySPEEDConsumer(aeui,4);
        ConsumerInterface speedconsumer_2 = new AlarmEntitySPEEDConsumer(aeui,5);
        ConsumerInterface speedconsumer_3 = new AlarmEntitySPEEDConsumer(aeui,6);
        
        new Thread(() -> speedconsumer_1.consumeData()).start();
        new Thread(() -> speedconsumer_2.consumeData()).start();
        new Thread(() -> speedconsumer_3.consumeData()).start();
    }

}
