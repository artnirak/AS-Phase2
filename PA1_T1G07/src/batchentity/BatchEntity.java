package batchentity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Lopes 76406
 */
public class BatchEntity {
    
    public static void main() {
        //consumeData();
    }
    public static void storeData(String data) {
        File file = new File(Paths.get(System.getProperty("user.dir"), "src", "data", "BATCH.txt").toString());
        
        try (Writer writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data + "\n");
            
        } catch (IOException e) {
            Logger.getLogger(BatchEntity.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
