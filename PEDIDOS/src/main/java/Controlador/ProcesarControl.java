package Controlador;

import Modelo.AppData;
import Modelo.Pedido;
import Modelo.Usuario;
import Path.Path;
import Tools.Tools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Modelo.Producto;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;

import javax.swing.*;
import java.io.IOException;

import static Dao.PedidoDAO.realizarPedido;

public class ProcesarControl {

    @FXML
    private Button FINAL;
    @FXML
    private Button REGRESAR;
    @FXML private Text u_name;
    @FXML private Text u_correo;

    // TIPO DE ENTREGA
    @FXML private RadioButton LOCAL;
    @FXML private RadioButton DELIVERY;

    // METODO DE PAGO
    @FXML private RadioButton EFECTIVO;
    @FXML private RadioButton YAPE;

    // Campo de dirección
    @FXML private TextArea direccionArea;

    @FXML
    private TableView<Producto> RESUMEN;
    @FXML private TableColumn<Producto, String> NOMBRE;
    @FXML private TableColumn<Producto, String> PRECIO;
    @FXML private TableColumn<Producto, String> CANTIDAD;
    @FXML private TableColumn<Producto, String> SUBTOTAL;

    private ObservableList<Producto> productosResumen = FXCollections.observableArrayList();
    public Usuario usuario = null;
    private double total = 0;
    private String direccion = "";
    private String metodo = "";

    public void initialize() {
        // Obtener el usuario y llenar los campos de texto
        usuario = AppData.getInstance().getUsuario();
        u_name.setText(usuario.getNombre() + " " + usuario.getApellido());
        u_correo.setText(usuario.getCorreo());

        // Configurar la tabla de resumen de productos
        RESUMEN.setItems(productosResumen);
        NOMBRE.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreProducto()));
        PRECIO.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().getPrecio())));
        CANTIDAD.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCantidad())));
        SUBTOTAL.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().getSubtotal())));

        // Inicialmente el botón FINAL está deshabilitado
        FINAL.setDisable(true);

        // Asegúrate de que el TextArea de dirección esté deshabilitado al inicio
        direccionArea.setDisable(true);

        // Crear ToggleGroups para los RadioButtons
        ToggleGroup group2 = new ToggleGroup();
        LOCAL.setToggleGroup(group2);
        DELIVERY.setToggleGroup(group2);

        ToggleGroup group1 = new ToggleGroup();
        EFECTIVO.setToggleGroup(group1);
        YAPE.setToggleGroup(group1);

        // Añadir listeners a los RadioButtons para habilitar el botón FINAL
        group2.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> habilitarFinalizar());
        group1.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> habilitarFinalizar());

        // Listener para los RadioButtons de entrega para habilitar/deshabilitar el campo de dirección
        group2.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (DELIVERY.isSelected()) {
                direccionArea.setDisable(false);  // Habilitar si está seleccionado DELIVERY
            } else {
                direccionArea.setDisable(true);   // Deshabilitar si está seleccionado LOCAL
            }
        });
    }

    // Habilitar el botón Finalizar solo si ambos grupos de RadioButton están seleccionados
    private void habilitarFinalizar() {
        // Habilitar el botón FINAL solo si hay una selección en ambos grupos
        if ((LOCAL.isSelected() || DELIVERY.isSelected()) && (EFECTIVO.isSelected() || YAPE.isSelected())) {
            FINAL.setDisable(false);
        } else {
            FINAL.setDisable(true);
        }
    }

    // Método que se llama al hacer clic en el botón "Finalizar"
    public void Finalizar() {
        // Verifica el tipo de entrega seleccionado
        if (DELIVERY.isSelected()) {
            direccion = direccionArea.getText(); // Tomamos la dirección ingresada en el TextArea
        }else{
            direccion = "Local";
        }

        // Establece el método de pago seleccionado
        if (EFECTIVO.isSelected()) {
            metodo = "Efectivo";
        } else if (YAPE.isSelected()) {
            metodo = "Yape";
        }

        // Crear el objeto Pedido con la información capturada
        Pedido pedido = new Pedido();
        pedido.setTotal(total);
        pedido.setIdUsuario(usuario.getIdUsuario());
        pedido.setEstado("Pendiente");
        pedido.setNombreCliente(usuario.getNombre());
        pedido.setMetodoPago(metodo);
        pedido.setDireccionEnvio(direccion);
        boolean confirmacion =  realizarPedido(pedido,productosResumen );
        if(confirmacion){
            JOptionPane.showMessageDialog(null, "Se realizo el pedido con exito");
        } else {
            JOptionPane.showMessageDialog(null, "Falla en el proceso de pedido");
        }
        // Imprimir los datos del pedido para depuración
        System.out.println(pedido.toString());

        // Aquí puedes agregar más lógica para procesar el pedido, como guardarlo en una base de datos o enviarlo a un servidor
    }

    // Método para establecer los productos en la tabla de resumen
    public void setProductos(ObservableList<Producto> productos) {
        productosResumen.addAll(productos);
    }

    @FXML
    private void regresar() {
        try {
            // Cargar la vista anterior (por ejemplo, Cliente.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.CLIENTE));
            Parent root = loader.load();

            // Obtener el Stage actual
            Stage stage = (Stage) REGRESAR.getScene().getWindow();

            // Cambiar a la escena anterior
            stage.setScene(new Scene(root));
            stage.setTitle("Pantalla de Pedido");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
