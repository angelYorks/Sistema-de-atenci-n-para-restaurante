package Controlador;

import Path.Path;
import Tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import static Tools.Tools.InsertImage;

public class ProductoUControl {
    @FXML
    private Label imagen;

    @FXML
    private Text nombre;

    @FXML
    private Text precio;

    @FXML
    private Button agregarButton;

    private String nombreProducto;
    private double precioProducto;

    private Runnable agregarProductoHandler;

    // Referencia al controlador principal
    private ClienteControl clienteControl;

    public void configurarProducto(String nombreProducto, double precioProducto, String imagenProducto) {
        this.nombre.setText(nombreProducto);
        this.precio.setText(String.valueOf(precioProducto));
        Tools.InsertImage(Path.LOGO, 100, 100, imagen);
    }

    // Método para establecer la acción al botón "Agregar"
    public void setAgregarProductoHandler(Runnable handler) {
        this.agregarProductoHandler = handler;

        agregarButton.setOnAction(event -> {
            if (agregarProductoHandler != null) {
                agregarProductoHandler.run();
            }
        });
    }
}

