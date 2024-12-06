package Logica;
import Dao.UsuarioDAO;
import Modelo.Usuario;

import javax.swing.*;

public class LoginLogic {

    public LoginLogic() {

    }
    public static Usuario iniciarSesion(String username, String password) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreoYContraseña(username, password);

        if (usuario != null) {
            return usuario;
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
            return null;
        }
    }
}