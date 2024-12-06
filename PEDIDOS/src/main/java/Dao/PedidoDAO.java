package Dao;


import Conexion.Conexion;
import Modelo.Detalles;
import Modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PedidoDAO {


    public static ObservableList<Pedido> obtenerPedidosPendientes() throws SQLException {
        ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
        String query = """
        SELECT p.id_pedido, u.nombre AS nombre_cliente, p.estado, p.total, p.fecha_pedido, p.metodo_pago, p.direccion_envio
        FROM Pedidos p
        JOIN Usuarios u ON p.id_usuario = u.id_usuario
        WHERE p.estado = 'Pendiente'
    """;

        try (Connection con = Conexion.getInstance().establecerConexion();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setNombreCliente(rs.getString("nombre_cliente"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setFechaPedido(rs.getTimestamp("fecha_pedido"));
                pedido.setMetodoPago(rs.getString("metodo_pago"));
                pedido.setDireccionEnvio(rs.getString("direccion_envio"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos pendientes: " + e.getMessage());
        }

        return pedidos;
    }



    public static ObservableList<Detalles> obtenerDetallesPorPedido(int idPedido) throws SQLException {
        ObservableList<Detalles> detalles = FXCollections.observableArrayList(); // Usamos ObservableList
        String query = "SELECT dp.id_detalle, dp.id_pedido, dp.id_producto, p.nombre_producto, "
                + "dp.cantidad, dp.precio_unitario, dp.subtotal, dp.estado_pedido "
                + "FROM DetallesPedido dp "
                + "JOIN Productos p ON dp.id_producto = p.id_producto "
                + "WHERE dp.id_pedido = ?";
        try (Connection con = Conexion.getInstance().establecerConexion();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Detalles detalle = new Detalles();
                    detalle.setIdDetalle(rs.getInt("id_detalle"));
                    detalle.setIdPedido(rs.getInt("id_pedido"));
                    detalle.setIdProducto(rs.getInt("id_producto"));
                    detalle.setNombreProducto(rs.getString("nombre_producto"));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    detalle.setEstado(rs.getString("estado_pedido"));
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles del pedido: " + e.getMessage());
            throw e;
        }
        return detalles;
    }

    public boolean asignarPedidoAPersonal(int idPedido, int idPersonal) {
        // Consultas SQL actualizadas
        String queryActividad = "INSERT INTO ActividadEmpleado (id_empleado, id_pedido, fecha_asignacion) VALUES (?, ?, NOW())";
        String queryEstadoPersonal = "UPDATE Usuarios SET estado = 'ocupado', fecha_estado = NOW() WHERE id_usuario = ? AND id_rol = 3"; // id_rol = 3 para personal
        String queryEstadoPedido = "UPDATE Pedidos SET estado = 'En Entrega', fecha_estado = NOW() WHERE id_pedido = ?";

        try (Connection con = Conexion.getInstance().establecerConexion(); PreparedStatement stmtActividad = con.prepareStatement(queryActividad); PreparedStatement stmtEstadoPersonal = con.prepareStatement(queryEstadoPersonal); PreparedStatement stmtEstadoPedido = con.prepareStatement(queryEstadoPedido)) {

            // Insertar la actividad en la tabla ActividadEmpleado
            stmtActividad.setInt(1, idPersonal);
            stmtActividad.setInt(2, idPedido);
            stmtActividad.executeUpdate();

            // Actualizar el estado del empleado a 'ocupado'
            stmtEstadoPersonal.setInt(1, idPersonal);
            stmtEstadoPersonal.executeUpdate();

            // Actualizar el estado del pedido a 'En Entrega'
            stmtEstadoPedido.setInt(1, idPedido);
            stmtEstadoPedido.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error al asignar pedido: " + e.getMessage());
            return false;
        }
    }

    public static boolean realizarPedido(Pedido pedido, ObservableList<Producto> productos) {
        String sqlPedido = "INSERT INTO Pedidos (id_usuario, estado, total, metodo_pago, direccion_envio) VALUES (?, ?, ?, ?, ?)";
        String sqlDetallePedido = "INSERT INTO DetallesPedido (id_pedido, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getInstance().establecerConexion()) {
            conn.setAutoCommit(false); // Iniciar transacción

            // Insertar el pedido
            try (PreparedStatement psPedido = conn.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psPedido.setInt(1, pedido.getIdUsuario());
                psPedido.setString(2, pedido.getEstado());
                psPedido.setDouble(3, pedido.getTotal());
                psPedido.setString(4, pedido.getMetodoPago());
                psPedido.setString(5, pedido.getDireccionEnvio());

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
                    for (Producto producto : productos) {
                        psDetallePedido.setInt(1, idPedido);
                        psDetallePedido.setInt(2, producto.getIdProducto());
                        psDetallePedido.setInt(3, producto.getCantidad());
                        psDetallePedido.setDouble(4, producto.getPrecio());
                        psDetallePedido.setDouble(5, producto.getPrecio()*producto.getCantidad());

                        psDetallePedido.executeUpdate();
                    }
                }

                conn.commit();
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

    public static void actualizarEstadoPedido(int idPedido, String estado) throws SQLException {
        String sql = "UPDATE detallespedido SET estado_pedido = ? WHERE id_detalle = ?";

        try (Connection con = Conexion.getInstance().establecerConexion(); // Establecer conexión
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setString(1, estado); // Asigna el nuevo estado
            statement.setInt(2, idPedido);  // Asigna el ID del pedido
            System.out.println("Se realizo el cambio de estado");
            statement.executeUpdate(); // Ejecuta la actualización
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el estado del pedido: " + e.getMessage());
        }
    }


}
