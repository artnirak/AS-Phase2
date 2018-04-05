package main;

import collectentity.CollectEntityHBProducer;
import gui.AlarmEntityUI;
import interfaces.Constantes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Lopes 76406
 */
public class AlarmEntityMain implements Constantes {
    public static void main(String[] args) {
        AlarmEntityUI aeui = new AlarmEntityUI();
        //TESTING
        processData(SPEED_FILE, aeui);
    }
    
    //METHOD FOR TESTING
    public static void processData(String filename, AlarmEntityUI aeui) {
        File file = new File(Paths.get(DATA_PATH, filename).toString());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            String[] s;
            while ((st = br.readLine()) != null) {
                //produceData(st);
                aeui.appendText(st);
                s = st.split(" ");
                int id = Integer.parseInt(s[0]), sp = Integer.parseInt(s[3]);
                if (sp > 100)
                    aeui.editTableRow(id, "on");
                else
                    aeui.editTableRow(id, "off");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(CollectEntityHBProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
