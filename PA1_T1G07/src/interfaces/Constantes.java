package interfaces;

import java.nio.file.Paths;

/**
 *
 * @author pedro
 */
public interface Constantes {
    public static final String HB_DIGESTION_CONSUMER_GROUP = "HB_D_GROUP";
    public static final String SPEED_DIGESTION_CONSUMER_GROUP = "SPEED_D_GROUP";
    public static final String STATUS_DIGESTION_CONSUMER_GROUP = "STATUS_D_GROUP";
    public static final String HB_BATCH_CONSUMER_GROUP = "HB_B_GROUP";
    public static final String SPEED_BATCH_CONSUMER_GROUP = "SPEED_B_GROUP";
    public static final String STATUS_BATCH_CONSUMER_GROUP = "STATUS_B_GROUP";
    public static final String SPEED_ALARM_CONSUMER_GROUP = "SPEED_A_GROUP";
    public static final String HB_FILE = "HB.txt";
    public static final String SPEED_FILE = "SPEED.txt";
    public static final String STATUS_FILE = "STATUS.txt";
    public static final String DATA_PATH = Paths.get(System.getProperty("user.dir"), "src", "data").toString();
}
