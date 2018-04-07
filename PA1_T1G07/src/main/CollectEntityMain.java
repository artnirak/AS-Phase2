package main;

import gui.CollectEntityUI;
import collectentity.CollectEntityHBProducer;
import collectentity.CollectEntitySPEEDProducer;
import collectentity.CollectEntitySTATUSProducer;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class CollectEntityMain {
    public static void main(String[] args) {
        CollectEntityUI ceui = new CollectEntityUI();
        
        CollectEntityHBProducer hbce = new CollectEntityHBProducer(ceui);
        CollectEntitySPEEDProducer speedce = new CollectEntitySPEEDProducer(ceui);
        CollectEntitySTATUSProducer statusce = new CollectEntitySTATUSProducer(ceui);
        
        new Thread(() -> hbce.processData()).start();
        new Thread(() -> speedce.processData()).start();
        new Thread(() -> statusce.processData()).start();
        
        
    }
}
