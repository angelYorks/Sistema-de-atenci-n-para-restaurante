package Tools;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Screen;

public class Tools {

    public static void InsertImage(String url, int width, int height, Label Logo){
        Image imagen = new Image(Tools.class.getResource(url).toExternalForm());
        ImageView imageView = new ImageView(imagen);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        Logo.setGraphic(imageView);
        Logo.setContentDisplay(ContentDisplay.CENTER);
        Logo.setAlignment(Pos.CENTER);
    }


    public static void newView(ActionEvent event, String url) {
        try {

            FXMLLoader loader = new FXMLLoader(Tools.class.getResource(url));
            Parent root = loader.load();


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();

            stage.setX((screenWidth - root.prefWidth(-1)) / 2);
            stage.setY((screenHeight - root.prefHeight(-1)) / 2);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    public static void MouseColor(String color, Button button){
        button.setStyle("-fx-fill: "+ color + ";");
    }
    public static void MouseColorNormal(Button button){
        button.setStyle("-fx-font-weight: normal;");
    }


}