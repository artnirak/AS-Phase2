package main;

import collectentity.CollectEntity;

/**
 *
 * @author Francisco Lopes 76406
 */
public class CollectEntityMain {
    public static void main(String[] args) {
        CollectEntity ce = new CollectEntity();
        
        new Thread(() -> ce.processData("HB.txt")).start();
        new Thread(() -> ce.processData("SPEED.txt")).start();
        new Thread(() -> ce.processData("STATUS.txt")).start();
        
        
    }
}
