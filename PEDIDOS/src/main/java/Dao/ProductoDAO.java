package Dao;
import Conexion.Conexion;
import Modelo.Producto;
import Modelo.ProductoU;
import Modelo.Usuario;
import Path.Path;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProductoDAO {

    // Método para obtener productos simplificados
    public void obtenerProductosSimplificados(JTable tablaProductos) {
        String query = "SELECT id_producto, nombre_producto, descripcion, precio, tiempo_preparacion FROM Productos";

        // Variables de conexión y resultset
        try (Connection con = Conexion.getInstance().establecerConexion();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
            modelo.setRowCount(0);

            if (modelo.getColumnCount() == 0) {
                modelo.addColumn("ID Producto");
                modelo.addColumn("Nombre");
                modelo.addColumn("Descripción");
                modelo.addColumn("Precio");
                modelo.addColumn("Tiempo Preparación");
            }

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("tiempo_preparacion")
                });
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los productos simplificados: " + e.getMessage());
        }
    }

    // Método para realizar un pedido
    public boolean realizarPedido(int usuarioId, String direccionEnvio, String metodoPago, JTable tablaCarrito, double totalPedido) {
        String sqlPedido = "INSERT INTO Pedidos (id_usuario, estado, total, metodo_pago, direccion_envio) VALUES (?, 'Pendiente', ?, ?, ?)";
        String sqlDetallePedido = "INSERT INTO DetallesPedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getInstance().establecerConexion()) {
            conn.setAutoCommit(false); // Iniciar transacción

            // Insertar el pedido
            try (PreparedStatement psPedido = conn.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psPedido.setInt(1, usuarioId);
                psPedido.setDouble(2, totalPedido);
                psPedido.setString(3, metodoPago);
                psPedido.setString(4, direccionEnvio);

                int filasAfectadas = psPedido.executeUpdate();
                if (filasAfectadas == 0) {
                    throw new SQLException("No se pudo crear el pedido");
                }

                // Obtener el ID generado del pedido
                ResultSet generatedKeys = psPedido.getGeneratedKeys();
                int idPedido = -1;
                if (generatedKeys.next()) {
                    idPedido = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del pedido");
                }

                // Insertar los detalles del pedido
                try (PreparedStatement psDetallePedido = conn.prepareStatement(sqlDetallePedido)) {
                    for (int i = 0; i < tablaCarrito.getRowCount(); i++) {
                        int idProducto = (int) tablaCarrito.getValueAt(i, 0);
                        int cantidad = (int) tablaCarrito.getValueAt(i, 3);
                        double precioUnitario = (double) tablaCarrito.getValueAt(i, 2);
                        double subtotal = (double) tablaCarrito.getValueAt(i, 4);

                        psDetallePedido.setInt(1, idPedido);
                        psDetallePedido.setInt(2, idProducto);
                        psDetallePedido.setInt(3, cantidad);
                        psDetallePedido.setDouble(4, precioUnitario);
                        psDetallePedido.setDouble(5, subtotal);

                        psDetallePedido.executeUpdate();
                    }
                }

                conn.commit(); // Confirmar transacción
                return true;
            } catch (SQLException e) {
                conn.rollback(); // Revertir cambios en caso de error
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Producto> ListaComidas(String categoria) throws SQLException {

        List<Producto> comidas = FXCollections.observableArrayList();

        String query = "SELECT * FROM productos WHERE id_categoria = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = Conexion.getInstance().establecerConexion();
            if (connection == null) {
                throw new SQLException("No se pudo establecer la conexión con la base de datos.");
            }

            stmt = connection.prepareStatement(query);
            stmt.setString(1, categoria);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Producto productou = new Producto();
                productou.setIdProducto(rs.getInt("id_producto"));
                productou.setNombreProducto(rs.getString("nombre_producto"));
                productou.setPrecio(rs.getDouble("precio"));
                productou.setUrl(Path.LOGO);

                comidas.add(productou);  // Agregar el objeto 'user' a la lista
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener las comidas: " + e.getMessage());
        } finally {
            // Cerrar los recursos en el bloque finally
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
        return comidas;
    }

}

