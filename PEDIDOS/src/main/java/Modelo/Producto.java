package Modelo;

public class Producto {

    private int idProducto;
    private String nombreProducto;
    private String descripcion;
    private double precio;
    private int tiempoPreparacion;
    private String url;
    private Double subtotal;
    private int cantidad = 1;

    // Constructor
    public Producto(int idProducto, String nombreProducto, String descripcion, double precio, int tiempoPreparacion) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public Producto(String nombre, Double precio, int cantidad, Double subtotal){
        this.nombreProducto = nombre;
        this.precio =precio;
        this.subtotal = subtotal;
    }
    public Producto(int id,String nombre, Double precio, int cantidad, Double subtotal){
        this.nombreProducto = nombre;
        this.precio =precio;
        this.subtotal = subtotal;
        this.idProducto = id;
    }
    public Producto(){
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Getters y setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }
}