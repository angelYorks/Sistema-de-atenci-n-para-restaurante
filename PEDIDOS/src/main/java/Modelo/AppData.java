
package Modelo;

import Modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppData {
    private static AppData instance;
    private Usuario usuario;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    private AppData() {}

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public ObservableList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ObservableList<Producto> productos) {
        this.productos.setAll(productos);
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
}

