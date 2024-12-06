package Modelo;

public class ProductoU {
    private String nombre;
    private String precio;
    private String imagen;

    public ProductoU(String nombre, String precio, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }
    public ProductoU(){}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }
}