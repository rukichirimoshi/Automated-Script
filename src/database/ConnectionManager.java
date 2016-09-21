package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import util.Constantes;

public class ConnectionManager {

    private String myUrl = "jdbc:mysql://10.141.0.43/prod_linde?useUnicode=true&characterEncoding=utf8";
    private String user = "root";
    private String pass = "Munich2015";

    private Connection conn = null;

    public Connection getConnection() {
        try {
            
//            if (!Constantes.LINDE_ENVIRONMENT) {
//                myUrl = "jdbc:mysql://sts06wk12/testes";
//                pass = "Munique";
//            }
            
            if (conn == null) {
                return DriverManager.getConnection(myUrl, user, pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        return conn;
    }
}
