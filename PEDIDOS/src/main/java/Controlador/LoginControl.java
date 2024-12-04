package Controlador;


import Tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import Path.Path;
import javafx.event.ActionEvent;

public class LoginControl {
    @FXML
    private Label Logo;

    @FXML
    private Button START;

    @FXML
    public void initialize(){
        Tools.InsertImage(Path.LOGO, 200, 209, Logo);
    }

    public void ViewClients(ActionEvent event){
        Tools.newView(event, Path.CLIENTEW);
    }

    public void MouseColor(){
        Tools.MouseColor("red", START);
    }
    public void MouseNormal(){
        Tools.MouseColorNormal(START);
    }
}
