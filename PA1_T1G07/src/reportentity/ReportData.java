package reportentity;

import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Francisco Lopes 76406
 */
public class ReportData {
    
    Connection conn;

    public ReportData(String filename) {
        createDataBase(filename);
    }

    private void createDataBase(String filename) {
        String url = "jdbc:sqlite:" + Paths.get(System.getProperty("user.dir"), "src", "reportentity", filename);
        String hbtable = "CREATE TABLE IF NOT EXISTS heartbeat (\n"
                + "car_id integer NOT NULL,\n"
                + "time integer NOT NULL,\n"
                + "car_reg text NOT NULL\n"
                + ");";

        String speedtable = "CREATE TABLE IF NOT EXISTS speed (\n"
                + "car_id integer NOT NULL,\n"
                + "time integer NOT NULL,\n"
                + "car_reg text NOT NULL,\n"
                + "speed integer NOT NULL,\n"
                + "localization integer NOT NULL,\n"
                + "max_speed integer NOT NULL,\n"
                + "alarm text NOT NULL\n"
                + ");";

        String statustable = "CREATE TABLE IF NOT EXISTS status (\n"
                + "car_id integer NOT NULL,\n"
                + "time integer NOT NULL,\n"
                + "car_reg text NOT NULL,\n"
                + "car_status text NOT NULL\n"
                + ");";

        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute(hbtable);
                stmt.execute(speedtable);
                stmt.execute(statustable);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateReport(String report) {
        String[] s = report.split(" ");

        switch (s[3]) {
            case "00":
                updateHB(s);
                break;
            case "01":
                updateSPEED(s);
                break;
            case "02":
                updateSTATUS(s);
                break;
        }
    }
    
    private void updateHB(String[] report) {
        String sql = "INSERT INTO heartbeat (car_id, time, car_reg) VALUES(?,?,?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(report[0]));
            pstmt.setInt(2, Integer.parseInt(report[1]));
            pstmt.setString(3, report[2]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void updateSPEED(String[] report) {
        String sql = "INSERT INTO speed (car_id, time, car_reg, speed, localization, max_speed, alarm) VALUES(?,?,?,?,?,?,?)";
        
        try {
            int speed = Integer.parseInt(report[4]), max_speed = Integer.parseInt(report[6]);
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(report[0]));
            pstmt.setInt(2, Integer.parseInt(report[1]));
            pstmt.setString(3, report[2]);
            pstmt.setInt(4, speed);
            pstmt.setInt(5, Integer.parseInt(report[5]));
            pstmt.setInt(6, Integer.parseInt(report[6]));
            if (speed > max_speed)
                pstmt.setString(7, "on");
            else
                pstmt.setString(7, "off");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void updateSTATUS(String[] report) {
        String sql = "INSERT INTO status (car_id, time, car_reg, car_status) VALUES(?,?,?,?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(report[0]));
            pstmt.setInt(2, Integer.parseInt(report[1]));
            pstmt.setString(3, report[2]);
            pstmt.setString(4, report[4]);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

}
