package main;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        AlarmEntityMain.main(args);
        Thread.sleep(1000);
        BatchEntityMain.main(args);
        Thread.sleep(1000);
        ReportEntityMain.main(args);
        Thread.sleep(1000);
        DigestionEntityMain.main(args);
        Thread.sleep(1000);
        CollectEntityMain.main(args);
    }
}
