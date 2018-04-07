package main;

/**
 *
 * @author Francisco Lopes 76406
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        AlarmEntityMain.main(args);
        Thread.sleep(500);
        BatchEntityMain.main(args);
        Thread.sleep(500);
        //ReportEntityMain.main(args);
        DigestionEntityMain.main(args);
        Thread.sleep(500);
        CollectEntityMain.main(args);   
    }
}
