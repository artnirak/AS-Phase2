package main;

import collectentity.CollectEntityUI;
import collectentity.HBProducer;
import collectentity.SPEEDProducer;
import collectentity.STATUSProducer;
import interfaces.Constantes;

/**
 *
 * @author Francisco Lopes 76406
 */
public class CollectEntityMain implements Constantes{
    public static void main(String[] args) {
        CollectEntityUI ceui = new CollectEntityUI();
        HBProducer hbce = new HBProducer(ceui);
        SPEEDProducer speedce = new SPEEDProducer(ceui);
        STATUSProducer statusce = new STATUSProducer(ceui);
        
        new Thread(() -> hbce.processData(hbfile)).start();
        new Thread(() -> speedce.processData(speedfile)).start();
        new Thread(() -> statusce.processData(statusfile)).start();
        
        
    }
}
