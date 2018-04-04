package main;

import gui.CollectEntityUI;
import collectentity.CollectEntityHBProducer;
import collectentity.CollectEntitySPEEDProducer;
import collectentity.CollectEntitySTATUSProducer;
import interfaces.Constantes;

/**
 *
 * @author Francisco Lopes 76406
 */
public class CollectEntityMain implements Constantes {
    public static void main(String[] args) {
        CollectEntityUI ceui = new CollectEntityUI();
        CollectEntityHBProducer hbce = new CollectEntityHBProducer(ceui);
        CollectEntitySPEEDProducer speedce = new CollectEntitySPEEDProducer(ceui);
        CollectEntitySTATUSProducer statusce = new CollectEntitySTATUSProducer(ceui);
        
        new Thread(() -> hbce.processData(HB_FILE)).start();
        new Thread(() -> speedce.processData(SPEED_FILE)).start();
        new Thread(() -> statusce.processData(STATUS_FILE)).start();
        
        
    }
}
