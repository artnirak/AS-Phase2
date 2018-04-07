package main;

import gui.ReportEntityUI;
import interfaces.ConsumerInterface;
import reportentity.ReportData;
import reportentity.ReportEntityHBConsumer;
import reportentity.ReportEntitySPEEDConsumer;
import reportentity.ReportEntitySTATUSConsumer;

/**
 *
 * @author Francisco Lopes 76406
 */
public class ReportEntityMain {
    public static void main(String[] args) {
        ReportEntityUI reui = new ReportEntityUI();
        ReportData rd = new ReportData("report.db");
        
        ConsumerInterface hbconsumer_1 = new ReportEntityHBConsumer(reui,rd,1);
        
        ConsumerInterface speedconsumer_1 = new ReportEntitySPEEDConsumer(reui,rd,4);
        ConsumerInterface speedconsumer_2 = new ReportEntitySPEEDConsumer(reui,rd,5);
        ConsumerInterface speedconsumer_3 = new ReportEntitySPEEDConsumer(reui,rd,6);
        
        ConsumerInterface statusconsumer_1 = new ReportEntitySTATUSConsumer(reui,rd,7);
        ConsumerInterface statusconsumer_2 = new ReportEntitySTATUSConsumer(reui,rd,8);
        ConsumerInterface statusconsumer_3 = new ReportEntitySTATUSConsumer(reui,rd,9);
        
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
