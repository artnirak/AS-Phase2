package main;

/**
 *
 * @author Francisco Lopes 76406
 * @author Pedro Gusmão 77867
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        AlarmEntityMain.main(args);
        BatchEntityMain.main(args);
        ReportEntityMain.main(args);
        DigestionEntityMain.main(args);
        Thread.sleep(5000);
        CollectEntityMain.main(args);
    }
}
