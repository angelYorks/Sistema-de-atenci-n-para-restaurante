package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {

    // Instancia estática de la clase Conexion (instancia única)
    private static Conexion instance;
    // Atributo Connection para la conexión a la base de datos
    private Connection conectar = null;

    // Parámetros de conexión
    private String usuario = "root";
    private String pass = "12345";
    private String bd = "Restaurante";
    private String ip = "127.0.0.1";
    private String puerto = "3306";

    private String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    // Constructor privado para evitar la creación de más instancias
    private Conexion() {
    }

    // Método estático para obtener la instancia de la clase (Singleton)
    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    // Método para establecer la conexión
    public Connection establecerConexion() {
        try {
            if (conectar == null || conectar.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conectar = DriverManager.getConnection(cadena, usuario, pass);
                //JOptionPane.showMessageDialog(null, "Conexión a la BD establecida.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la BD: " + e.getMessage());
        }
        return conectar;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        try {
            if (conectar != null && !conectar.isClosed()) {
                conectar.close();
                //JOptionPane.showMessageDialog(null, "Conexión cerrada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage());
        }
    }
}