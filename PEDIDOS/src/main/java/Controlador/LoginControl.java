package Controlador;


import Logica.LoginLogic;
import Modelo.AppData;
import Modelo.Usuario;
import Tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import Path.Path;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class LoginControl {
    private Stage primaryStage;
    @FXML
    private Label Logo;

    @FXML
    private TextField CORREO;

    @FXML
    private TextField CONTRASENA;

    @FXML
    private Button LOGEAR;

    @FXML
    public void initialize()
    {
        Tools.InsertImage(Path.LOGO, 200, 209, Logo);

    }

    public void MouseColor(){
        Tools.MouseColor("red", LOGEAR);
    }
    public void MouseNormal(){
        Tools.MouseColorNormal(LOGEAR);
    }

    public void Ingresar(ActionEvent event) {
        String correo = CORREO.getText();
        String contrasena = CONTRASENA.getText();

        if (correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            System.out.println("El correo o la contraseña están vacíos.");
            return;
        }

        Usuario ventana = LoginLogic.iniciarSesion(correo, contrasena);

        // Guardar el objeto Usuario en AppData
        AppData.getInstance().setUsuario(ventana);

        switch (ventana.obtenerRol()) {
            case "cliente":
                Tools.newView(event, Path.CLIENTE);
                break;
            case "chef":
                Tools.newView(event, Path.COCINA);
                break;
            case "personal":
                Tools.newView(event, Path.PERSONAL);
                break;
            default:
                System.out.println("ERROR DE INTERFAZ: " + ventana);
                break;
        }
    }

}
