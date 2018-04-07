package interfaces;

import java.nio.file.Paths;

/**
 *
 * @author pedro
 */
public interface Constantes {
    //Digestion consumer groups
    public static final String HB_DIGESTION_CONSUMER_GROUP = "HB_D_GROUP";
    public static final String SPEED_DIGESTION_CONSUMER_GROUP = "SPEED_D_GROUP";
    public static final String STATUS_DIGESTION_CONSUMER_GROUP = "STATUS_D_GROUP";
    
    //Batch consumer groups
    public static final String HB_BATCH_CONSUMER_GROUP = "HB_B_GROUP";
    public static final String SPEED_BATCH_CONSUMER_GROUP = "SPEED_B_GROUP";
    public static final String STATUS_BATCH_CONSUMER_GROUP = "STATUS_B_GROUP";
    
    //Alarm consumer groups
    public static final String SPEED_ALARM_CONSUMER_GROUP = "SPEED_A_GROUP";
    
    //Report consumer groups
    public static final String HB_REPORT_CONSUMER_GROUP = "HB_R_GROUP";
    public static final String SPEED_REPORT_CONSUMER_GROUP = "SPEED_R_GROUP";
    public static final String STATUS_REPORT_CONSUMER_GROUP = "STATUS_R_GROUP";
    
    //Data file names
    public static final String HB_FILE = "HB.txt";
    public static final String SPEED_FILE = "SPEED.txt";
    public static final String STATUS_FILE = "STATUS.txt";
    
    //Path to data files
    public static final String DATA_PATH = Paths.get(System.getProperty("user.dir"), "src", "data").toString();
}
