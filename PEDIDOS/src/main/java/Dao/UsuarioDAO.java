package Dao;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Modelo.Producto;
import javafx.collections.FXCollections;
import Modelo.Usuario;
import javafx.collections.ObservableList;

public class UsuarioDAO {

    // Método para obtener un usuario por correo y contraseña
    public Usuario obtenerUsuarioPorCorreoYContraseña(String correo, String contraseña) {
        String query = "SELECT * FROM Usuarios WHERE correo = ? AND contraseña = ?";

        // Declarar las variables de conexión, statement y resultset fuera del try-catch
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión desde la clase Conexion
            connection = Conexion.getInstance().establecerConexion();

            // Verificar si la conexión es nula
            if (connection == null) {
                throw new SQLException("No se pudo establecer la conexión con la base de datos.");
            }

            // Preparar el statement con la consulta
            stmt = connection.prepareStatement(query);
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            // Ejecutar la consulta
            rs = stmt.executeQuery();

            // Verificar si se obtuvo un usuario
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("contraseña"),
                        rs.getInt("id_rol"),
                        rs.getTimestamp("fecha_registro")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el usuario por correo y contraseña: " + e.getMessage());
        } finally {
            // Cerrar los recursos en el bloque finally para garantizar que siempre se cierren
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
        return null; // Retornar null si no se encuentra el usuario
    }

    public static ObservableList<Usuario> ListaCocina() throws SQLException {

        ObservableList<Usuario> cocineros = FXCollections.observableArrayList();

        String query = "SELECT * FROM usuarios WHERE id_rol = 2";  // Suponiendo que '2' es el rol de Cocinero
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = Conexion.getInstance().establecerConexion();
            if (connection == null) {
                throw new SQLException("No se pudo establecer la conexión con la base de datos.");
            }

            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario user = new Usuario();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido(rs.getString("apellido"));
                user.setCorreo(rs.getString("correo"));
                user.setContraseña(rs.getString("contraseña"));
                user.setIdRol(rs.getInt("id_rol"));

                cocineros.add(user);  // Agregar el objeto 'user' a la lista
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los cocineros: " + e.getMessage());
        } finally {
            // Cerrar los recursos en el bloque finally
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
        return cocineros;
    }


}
