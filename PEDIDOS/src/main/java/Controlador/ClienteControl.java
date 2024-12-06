package Controlador;
import Factory.ProductoUFactory;
import Modelo.AppData;
import Modelo.Producto;
import Modelo.ProductoU;
import Modelo.Usuario;
import Path.Path;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static Dao.ProductoDAO.ListaComidas;


public class ClienteControl {

    @FXML
    private TableView<Producto> PEDIDO;
    @FXML private TableColumn<Producto, String> NOMBRE;
    @FXML private TableColumn<Producto, String> PRECIO;
    @FXML private TableColumn<Producto, String> CANTIDAD;
    @FXML private TableColumn<Producto, String> SUBTOTAL;

    @FXML
    private Button ELIMINAR;
    @FXML
    private Text TITULO;

    @FXML
    private Button PROCESAR;

    @FXML
    private Tab tabComidas;

    @FXML
    private Tab tabBebidas;

    @FXML
    private Tab tabOtros;
    @FXML
    private GridPane gridPane;

    private ObservableList<Producto> productosTabla = FXCollections.observableArrayList();
    private Usuario usuario = null;

    public void initialize() throws SQLException {

        Usuario datos_usuario = AppData.getInstance().getUsuario();
        if (datos_usuario !=null) {
            usuario = datos_usuario;
        }
        TITULO.setText("Hola "+ usuario.getNombre());

        System.out.println("Usuario logueado: " + usuario.getNombre());
        ObservableList<Producto> datosGuardados = AppData.getInstance().getProductos();
        if (!datosGuardados.isEmpty()) {
            productosTabla.setAll(datosGuardados);
        }


        PEDIDO.setItems(productosTabla);

        NOMBRE.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombreProducto()));
        PRECIO.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().getPrecio())));
        CANTIDAD.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCantidad())));
        SUBTOTAL.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().getSubtotal())));


        ELIMINAR.setOnAction(event -> eliminarProducto());


        List<Producto> categoria1 = ListaComidas("1");
        List<Producto> categoria2 = ListaComidas("3");
        List<Producto> categoria3 = ListaComidas("2");

        agregarTab("COMIDAS", categoria1, tabComidas);
        agregarTab("BEBIDAS", categoria2, tabBebidas);
        agregarTab("OTROS", categoria3, tabOtros);
    }


    private void agregarTab(String titulo, List<Producto> productos, Tab tab) {
        VBox contenedorProductos = new VBox(10);
        HBox fila = new HBox(10);
        int column = 0;

        for (Producto producto : productos) {
            AnchorPane productoPane = ProductoUFactory.crearProducto(
                    producto.getIdProducto(),
                    producto.getNombreProducto(),
                    producto.getPrecio(),
                    producto.getUrl(),
                    this  // Pasar la referencia de ClienteControl
            );

            fila.getChildren().add(productoPane);
            column++;

            if (column == 3) {
                contenedorProductos.getChildren().add(fila);
                fila = new HBox(10);
                column = 0;
            }
        }

        if (column > 0) {
            contenedorProductos.getChildren().add(fila);
        }

        tab.setText(titulo);
        tab.setContent(contenedorProductos);
    }

    // Método para agregar productos a la tabla
    public void agregarProductoATabla(int id,String nombre, double precio) {

        for (Producto producto : productosTabla) {
            if (producto.getNombreProducto().equals(nombre)) {
                producto.setCantidad(producto.getCantidad() + 1);
                producto.setSubtotal(producto.getPrecio() * producto.getCantidad());
                PEDIDO.refresh();
                return;
            }
        }

        Producto nuevoProducto = new Producto(id, nombre, precio, 1, precio);
        productosTabla.add(nuevoProducto);
    }

    private void eliminarProducto() {
        Producto productoSeleccionado = PEDIDO.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            productoSeleccionado.setCantidad(productoSeleccionado.getCantidad() - 1);

            if (productoSeleccionado.getCantidad() <= 0) {
                productosTabla.remove(productoSeleccionado);
            } else {
                productoSeleccionado.setSubtotal(productoSeleccionado.getPrecio() * productoSeleccionado.getCantidad());
            }

            // Refrescar la tabla para reflejar los cambios
            PEDIDO.refresh();
        } else {
            // Si no hay ningún producto seleccionado, puedes mostrar un mensaje de advertencia
            System.out.println("Por favor, selecciona un producto para eliminar.");
        }
    }

    @FXML
    private void procesarPedido() {
        try {
            // Guardar los datos de la tabla en el singleton
            AppData.getInstance().setProductos(productosTabla);
            AppData.getInstance().setUsuario(usuario);

            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.PROCESO));
            Parent root = loader.load();

            // Obtener el controlador de la nueva pantalla
            ProcesarControl resumenControl = loader.getController();
            resumenControl.setProductos(productosTabla);

            // Cambiar la escena actual
            Stage stage = (Stage) PROCESAR.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Resumen del Pedido");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
