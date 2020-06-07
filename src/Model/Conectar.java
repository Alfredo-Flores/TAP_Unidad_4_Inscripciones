package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conectar {
    Connection con = null;
    public Connection conexion() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/inscripciones", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
