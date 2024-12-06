package Controlador;

import Dao.PedidoDAO;
import Modelo.Detalles;
import Modelo.Pedido;
import Modelo.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.sql.Timestamp;

import static Dao.PedidoDAO.obtenerDetallesPorPedido;
import static Dao.PedidoDAO.obtenerPedidosPendientes;
import static Dao.UsuarioDAO.ListaCocina;

public class ChefControl {

    @FXML
    private TableView<Pedido> T_PENDIENTES;
    @FXML private TableColumn<Pedido, Integer> PEN_ID;
    @FXML private TableColumn<Pedido, String> PEN_CL;
    @FXML private TableColumn<Pedido, String> PEN_ES;
    @FXML private TableColumn<Pedido, Timestamp> PEN_FE;
    @FXML private TableColumn<Pedido, String> PEN_HO; // Cambié de Timestamp a String para mostrar solo la hora

    @FXML
    private TableView<Detalles> T_PEDIDO;
    @FXML private TableColumn<Detalles, Integer> PD_ID;
    @FXML private TableColumn<Detalles, String> PD_PR;
    @FXML private TableColumn<Detalles, Integer> PD_CA;
    @FXML private TableColumn<Detalles, String> PD_ES;
    int idPedido = 0;

    @FXML private Button CANCELAR;
    @FXML private Button PROCESAR;
    @FXML private Button FINALIZAR;
    @FXML private Button REGRESAR;
    private int idDetalle = 0;

    // Método de inicialización
    @FXML
    public void initialize() {
        inicializarTablaPendientes();
        inicializarTablaDetalles();

        // Listener para obtener el ID del pedido seleccionado
        T_PENDIENTES.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idPedido = newValue.getIdPedido(); // Obtener el ID del pedido seleccionado
                cargarDetallesPedido(idPedido); // Llamar al método para cargar detalles
                System.out.println("el pedido seleccionado es:"+idPedido);
            }
        });

        T_PEDIDO.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idDetalle = newValue.getIdDetalle(); // Obtener el ID del pedido seleccionado
                System.out.println("el producto seleccionado es "+idDetalle);
            }
        });

    }


    // Inicializa la tabla de pedidos pendientes
    public void inicializarTablaPendientes() {
        PEN_ID.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
        PEN_CL.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        PEN_ES.setCellValueFactory(new PropertyValueFactory<>("estado"));
        PEN_FE.setCellValueFactory(new PropertyValueFactory<>("fechaPedido"));

        // Cambié a SimpleStringProperty para mostrar solo la hora
        PEN_HO.setCellValueFactory(cellData -> {
            Timestamp timestamp = cellData.getValue().getFechaPedido();
            return (timestamp != null) ? new SimpleStringProperty(timestamp.toLocalDateTime().toLocalTime().toString()) : new SimpleStringProperty("");
        });

        try {
            ObservableList<Pedido> pedidos = obtenerPedidosPendientes();
            T_PENDIENTES.setItems(pedidos);
        } catch (SQLException e) {
            System.err.println("Error al obtener los pedidos: " + e.getMessage());
        }
    }

    // Inicializa la tabla de detalles de pedidos
    // Inicializa la tabla de detalles de pedidos
    public void inicializarTablaDetalles() {
        PD_ID.setCellValueFactory(new PropertyValueFactory<>("idDetalle"));
        PD_PR.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        PD_ES.setCellValueFactory(new PropertyValueFactory<>("estado"));
        PD_CA.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Cambiar el color del texto en la columna de estado (PD_ES)
        PD_ES.setCellFactory(column -> {
            return new TableCell<Detalles, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.isEmpty()) {
                        // Si la celda está vacía, limpiar el texto
                        setText("");
                        setTextFill(javafx.scene.paint.Color.BLACK);  // Restablecer al color predeterminado
                    } else {
                        // Cambiar el color dependiendo del estado
                        if (item.equals("Cancelado")) {
                            setTextFill(javafx.scene.paint.Color.RED);  // Rojo para "Cancelado"
                        } else if (item.equals("En proceso")) {
                            setTextFill(javafx.scene.paint.Color.ORANGE);  // Naranja para "En proceso"
                        } else if (item.equals("Finalizado")) {
                            setTextFill(javafx.scene.paint.Color.GREEN);  // Verde para "Finalizado"
                        } else {
                            setTextFill(javafx.scene.paint.Color.BLACK);  // Negro por defecto
                        }
                        setText(item);  // Establecer el texto en la celda
                    }
                }
            };
        });
    }


    // Carga los detalles del pedido seleccionado
    public void cargarDetallesPedido(int idPedido) {
        try {
            ObservableList<Detalles> detalles = obtenerDetallesPorPedido(idPedido); // Llamar al DAO con el ID del pedido
            T_PEDIDO.setItems(detalles); // Mostrar los detalles en la tabla T_PEDIDO
        } catch (SQLException e) {
            System.err.println("Error al cargar los detalles del pedido: " + e.getMessage());
        }
    }

    @FXML
    public void cancelarProducto() {
        if (idDetalle > 0) {  // Verifica que haya un detalle seleccionado
            try {
                PedidoDAO.actualizarEstadoPedido(idDetalle, "Cancelado");
                actualizarTablaDetalles();  // Actualiza la tabla después de la actualización
                System.out.println("Producto cancelado: " + idDetalle);
            } catch (SQLException e) {
                System.err.println("Error al cancelar el producto: " + e.getMessage());
            }
        } else {
            System.err.println("No se ha seleccionado ningún producto.");
        }
    }

    // Método para procesar un producto
    @FXML
    public void procesarProducto() {
        if (idDetalle > 0) {
            try {
                PedidoDAO.actualizarEstadoPedido(idDetalle, "En proceso");
                actualizarTablaDetalles();
                System.out.println("Producto en proceso: " + idDetalle);
            } catch (SQLException e) {
                System.err.println("Error al procesar el producto: " + e.getMessage());
            }
        } else {
            System.err.println("No se ha seleccionado ningún producto.");
        }
    }

    // Método para finalizar un producto
    @FXML
    public void finalizarProducto() {
        if (idDetalle > 0) {
            try {
                PedidoDAO.actualizarEstadoPedido(idDetalle, "Finalizado");
                actualizarTablaDetalles();
                System.out.println("Producto finalizado: " + idDetalle);
            } catch (SQLException e) {
                System.err.println("Error al finalizar el producto: " + e.getMessage());
            }
        } else {
            System.err.println("No se ha seleccionado ningún producto.");
        }
    }

    // Método para actualizar la tabla de detalles de pedidos
    private void actualizarTablaDetalles() {
        try {
            if (idPedido > 0) {
                ObservableList<Detalles> detalles = obtenerDetallesPorPedido(idPedido);  // Llamar al DAO para obtener los detalles actualizados
                T_PEDIDO.setItems(detalles);  // Actualizar los detalles en la tabla
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la tabla de detalles: " + e.getMessage());
        }
    }


}
