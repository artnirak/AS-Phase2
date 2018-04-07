package main;

/**
 *
 * @author Francisco Lopes 76406
 */
public class Main {
    public static void main(String[] args) {
        AlarmEntityMain.main(args);
        BatchEntityMain.main(args);
        //new Thread(() -> ReportEntityMain.main(args)).start();
        DigestionEntityMain.main(args);
        CollectEntityMain.main(args);   
    }
}
