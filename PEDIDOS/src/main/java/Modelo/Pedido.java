package Modelo;

import javafx.beans.property.*;

import java.sql.Timestamp;

public class Pedido {
    private IntegerProperty idPedido;
    private StringProperty nombreCliente;
    private StringProperty estado;
    private DoubleProperty total;
    private ObjectProperty<Timestamp> fechaPedido;
    private StringProperty metodoPago;
    private StringProperty direccionEnvio;
    private IntegerProperty idUsuario;

    // Constructor vacío
    public Pedido() {
        this.idPedido = new SimpleIntegerProperty();
        this.nombreCliente = new SimpleStringProperty();
        this.estado = new SimpleStringProperty();
        this.total = new SimpleDoubleProperty();
        this.fechaPedido = new SimpleObjectProperty<>();
        this.metodoPago = new SimpleStringProperty();
        this.direccionEnvio = new SimpleStringProperty();
        this.idUsuario = new SimpleIntegerProperty();
    }

    // Getters y Setters usando las propiedades observables
    public int getIdPedido() {
        return idPedido.get();
    }

    public void setIdPedido(int idPedido) {
        this.idPedido.set(idPedido);
    }

    public IntegerProperty idPedidoProperty() {
        return idPedido;
    }

    public int getIdUsuario() {
        return idUsuario.get();
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario.set(idUsuario);
    }

    public IntegerProperty idUsuarioProperty() {
        return idUsuario;
    }

    public String getNombreCliente() {
        return nombreCliente.get();
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente.set(nombreCliente);
    }

    public StringProperty nombreClienteProperty() {
        return nombreCliente;
    }

    public String getEstado() {
        return estado.get();
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    public StringProperty estadoProperty() {
        return estado;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public Timestamp getFechaPedido() {
        return fechaPedido.get();
    }

    public void setFechaPedido(Timestamp fechaPedido) {
        this.fechaPedido.set(fechaPedido);
    }

    public ObjectProperty<Timestamp> fechaPedidoProperty() {
        return fechaPedido;
    }

    public String getMetodoPago() {
        return metodoPago.get();
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago.set(metodoPago);
    }

    public StringProperty metodoPagoProperty() {
        return metodoPago;
    }

    public String getDireccionEnvio() {
        return direccionEnvio.get();
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio.set(direccionEnvio);
    }

    public StringProperty direccionEnvioProperty() {
        return direccionEnvio;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido.get() +
                ", nombreCliente='" + nombreCliente.get() + '\'' +
                ", estado='" + estado.get() + '\'' +
                ", total=" + total.get() +
                ", fechaPedido=" + fechaPedido.get() +
                ", metodoPago='" + metodoPago.get() + '\'' +
                ", direccionEnvio='" + direccionEnvio.get() + '\'' +
                '}';
    }
}
