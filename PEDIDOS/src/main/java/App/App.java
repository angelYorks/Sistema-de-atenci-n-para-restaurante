package App;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Path.Path;

public class App extends Application{
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane load = FXMLLoader.load(getClass().getResource(Path.LOGIN));
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }
}