package dao;

import model.OrderItem;
import exception.DBException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO implements DAO<OrderItem> {
    @Override
    public void add(OrderItem orderItem) throws DBException {
        String sql = "INSERT INTO orderitems (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error adding order item: " + e.getMessage());
        }
    }

    @Override
    public OrderItem getById(int id) throws DBException {
        String sql = "SELECT * FROM orderitems WHERE OrderItemID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("OrderItemID"));
                orderItem.setOrderId(rs.getInt("OrderID"));
                orderItem.setProductId(rs.getInt("ProductID"));
                orderItem.setQuantity(rs.getInt("Quantity"));
                orderItem.setPrice(rs.getDouble("Price"));
                return orderItem;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("Error retrieving order item: " + e.getMessage());
        }
    }

    @Override
    public List<OrderItem> getAll() throws DBException {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM orderitems";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("OrderItemID"));
                orderItem.setOrderId(rs.getInt("OrderID"));
                orderItem.setProductId(rs.getInt("ProductID"));
                orderItem.setQuantity(rs.getInt("Quantity"));
                orderItem.setPrice(rs.getDouble("Price"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            throw new DBException("Error retrieving order items: " + e.getMessage());
        }
        return orderItems;
    }

    public List<OrderItem> getByOrderId(int orderId) throws DBException {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM orderitems WHERE OrderID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("OrderItemID"));
                orderItem.setOrderId(rs.getInt("OrderID"));
                orderItem.setProductId(rs.getInt("ProductID"));
                orderItem.setQuantity(rs.getInt("Quantity"));
                orderItem.setPrice(rs.getDouble("Price"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            throw new DBException("Error retrieving order items by order: " + e.getMessage());
        }
        return orderItems;
    }

    @Override
    public void update(OrderItem orderItem) throws DBException {
        String sql = "UPDATE orderitems SET OrderID = ?, ProductID = ?, Quantity = ?, Price = ? WHERE OrderItemID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getPrice());
            stmt.setInt(5, orderItem.getOrderItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error updating order item: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DBException {
        String sql = "DELETE FROM orderitems WHERE OrderItemID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error deleting order item: " + e.getMessage());
        }
    }
}