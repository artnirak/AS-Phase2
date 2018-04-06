package interfaces;

import java.nio.file.Paths;

/**
 *
 * @author pedro
 */
public interface Constantes {
    public static final String HB_DIGESTION_CONSUMER_GROUP = "HBD_GROUP";
    public static final String SPEED_DIGESTION_CONSUMER_GROUP = "SPEEDD_GROUP";
    public static final String STATUS_DIGESTION_CONSUMER_GROUP = "STATUSD_GROUP";
    public static final String HB_FILE = "HB.txt";
    public static final String SPEED_FILE = "SPEED.txt";
    public static final String STATUS_FILE = "STATUS.txt";
    public static final String DATA_PATH = Paths.get(System.getProperty("user.dir"), "src", "data").toString();
}
