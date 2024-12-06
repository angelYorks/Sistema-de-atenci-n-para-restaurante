package Conexion;

import java.sql.Connection;

public class prueba {
    public static void main(String[] args) {
        Connection cs= null;
        cs = Conexion.getInstance().establecerConexion();
    }
}
