package dao;

import model.Order;
import exception.DBException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements DAO<Order> {
    @Override
    public void add(Order order) throws DBException {
        String sql = "INSERT INTO orders (CustomerID, OrderDate, OrderStatus, PaymentStatus, ShippingAddress, BillingAddress, TrackingNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setTimestamp(2, order.getOrderDate());
            stmt.setString(3, order.getOrderStatus());
            stmt.setString(4, order.getPaymentStatus());
            stmt.setString(5, order.getShippingAddress());
            stmt.setString(6, order.getBillingAddress());
            stmt.setString(7, order.getTrackingNumber());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DBException("Error adding order: " + e.getMessage());
        }
    }

    @Override
    public Order getById(int id) throws DBException {
        String sql = "SELECT * FROM orders WHERE OrderID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("OrderID"));
                order.setCustomerId(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setPaymentStatus(rs.getString("PaymentStatus"));
                order.setShippingAddress(rs.getString("ShippingAddress"));
                order.setBillingAddress(rs.getString("BillingAddress"));
                order.setTrackingNumber(rs.getString("TrackingNumber"));
                return order;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("Error retrieving order: " + e.getMessage());
        }
    }

    @Override
    public List<Order> getAll() throws DBException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("OrderID"));
                order.setCustomerId(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setPaymentStatus(rs.getString("PaymentStatus"));
                order.setShippingAddress(rs.getString("ShippingAddress"));
                order.setBillingAddress(rs.getString("BillingAddress"));
                order.setTrackingNumber(rs.getString("TrackingNumber"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DBException("Error retrieving orders: " + e.getMessage());
        }
        return orders;
    }

    public List<Order> getByCustomerId(int customerId) throws DBException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE CustomerID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("OrderID"));
                order.setCustomerId(rs.getInt("CustomerID"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                order.setOrderStatus(rs.getString("OrderStatus"));
                order.setPaymentStatus(rs.getString("PaymentStatus"));
                order.setShippingAddress(rs.getString("ShippingAddress"));
                order.setBillingAddress(rs.getString("BillingAddress"));
                order.setTrackingNumber(rs.getString("TrackingNumber"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DBException("Error retrieving orders by customer: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public void update(Order order) throws DBException {
        String sql = "UPDATE orders SET CustomerID = ?, OrderDate = ?, OrderStatus = ?, PaymentStatus = ?, ShippingAddress = ?, BillingAddress = ?, TrackingNumber = ? WHERE OrderID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setTimestamp(2, order.getOrderDate());
            stmt.setString(3, order.getOrderStatus());
            stmt.setString(4, order.getPaymentStatus());
            stmt.setString(5, order.getShippingAddress());
            stmt.setString(6, order.getBillingAddress());
            stmt.setString(7, order.getTrackingNumber());
            stmt.setInt(8, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error updating order: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DBException {
        String sql = "DELETE FROM orders WHERE OrderID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error deleting order: " + e.getMessage());
        }
    }
}