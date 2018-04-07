package main;

import gui.ReportEntityUI;
import reportentity.ReportData;

/**
 *
 * @author Francisco Lopes 76406
 */
public class ReportEntityMain {
    public static void main(String[] args) {
        //ReportEntityUI reui = new ReportEntityUI();
        ReportData report = new ReportData("report.db");
        report.updateReport("12 1234 XX-YY-12 00");
    }
}
