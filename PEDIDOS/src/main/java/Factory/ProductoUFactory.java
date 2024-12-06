package Factory;
import Controlador.ClienteControl;
import Controlador.ProductoUControl;
import Modelo.Producto;
import Modelo.ProductoU;
import Path.Path;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductoUFactory {
    public static AnchorPane crearProducto(int id, String nombre, Double precio, String imagenRuta, ClienteControl clienteControl) {
        try {
            FXMLLoader loader = new FXMLLoader(ProductoUFactory.class.getResource(Path.PU));
            AnchorPane productoPane = loader.load();

            // Configurar controlador del producto
            ProductoUControl controlador = loader.getController();
            controlador.configurarProducto(nombre, precio, imagenRuta);

            // Configurar la acción del botón "Agregar"
            controlador.setAgregarProductoHandler(() -> clienteControl.agregarProductoATabla(id,nombre, precio));

            return productoPane;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    // Método para crear múltiples productos
    public static List<AnchorPane> crearProductos(List<Producto> productos, ClienteControl clienteControl) {
        List<AnchorPane> listaProductos = new ArrayList<>();
        for (Producto producto : productos) {
            // Pasamos la referencia al controlador principal
            listaProductos.add(crearProducto(
                    producto.getIdProducto(),
                    producto.getNombreProducto(),
                    producto.getPrecio(),
                    producto.getUrl(),
                    clienteControl
            ));
        }
        return listaProductos;
    }

}
