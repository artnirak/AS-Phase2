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
 * @author Pedro Gusmão 77867
 */
public class ReportEntityMain {
    public static void main(String[] args) {
        ReportEntityUI reui = new ReportEntityUI();
        ReportData rd = new ReportData("report.db");
        
        ConsumerInterface hbconsumer_1 = new ReportEntityHBConsumer(reui,rd);
        ConsumerInterface hbconsumer_2 = new ReportEntityHBConsumer(reui,rd);
        ConsumerInterface hbconsumer_3 = new ReportEntityHBConsumer(reui,rd);
        
        ConsumerInterface speedconsumer_1 = new ReportEntitySPEEDConsumer(reui,rd);
        
        ConsumerInterface statusconsumer_1 = new ReportEntitySTATUSConsumer(reui,rd);
        
        //parallel consumption each consumer is atributed a partition
        new Thread(() -> hbconsumer_1.consumeData()).start();
        new Thread(() -> hbconsumer_2.consumeData()).start();
        new Thread(() -> hbconsumer_3.consumeData()).start();
        
        new Thread(() -> speedconsumer_1.consumeData()).start();
        
        new Thread(() -> statusconsumer_1.consumeData()).start();
        
    }
}
