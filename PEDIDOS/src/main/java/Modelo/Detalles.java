package Modelo;

public class Detalles {

    private int idDetalle;
    private int idPedido;
    private int idProducto;
    private String nombreProducto; // Campo para el nombre del producto
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private String estado = null;

    // Constructor vacío
    public Detalles() {
    }

    // Constructor con parámetros
    public Detalles(int idDetalle, int idPedido, int idProducto, int cantidad, double precioUnitario, double subtotal) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Detalles(int idDetalle, int idPedido, int idProducto, String nombreProducto, int cantidad, double precioUnitario, double subtotal) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    // Getters y Setters
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetallePedido{"
                + "idDetalle=" + idDetalle
                + ", idPedido=" + idPedido
                + ", idProducto=" + idProducto
                + ", cantidad=" + cantidad
                + ", precioUnitario=" + precioUnitario
                + ", subtotal=" + subtotal
                + '}';
    }
}
