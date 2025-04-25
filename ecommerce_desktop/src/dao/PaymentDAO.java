package dao;

import model.Payment;
import exception.DBException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements DAO<Payment> {
    @Override
    public void add(Payment payment) throws DBException {
        String sql = "INSERT INTO payments (OrderID, CustomerID, PaymentMethod, PaymentAmount, PaymentDate, PaymentStatus, TransactionID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getOrderId());
            stmt.setInt(2, payment.getCustomerId());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setDouble(4, payment.getPaymentAmount());
            stmt.setTimestamp(5, payment.getPaymentDate());
            stmt.setString(6, payment.getPaymentStatus());
            stmt.setString(7, payment.getTransactionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error adding payment: " + e.getMessage());
        }
    }

    @Override
    public Payment getById(int id) throws DBException {
        String sql = "SELECT * FROM payments WHERE PaymentID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("PaymentID"));
                payment.setOrderId(rs.getInt("OrderID"));
                payment.setCustomerId(rs.getInt("CustomerID"));
                payment.setPaymentMethod(rs.getString("PaymentMethod"));
                payment.setPaymentAmount(rs.getDouble("PaymentAmount"));
                payment.setPaymentDate(rs.getTimestamp("PaymentDate"));
                payment.setPaymentStatus(rs.getString("PaymentStatus"));
                payment.setTransactionId(rs.getString("TransactionID"));
                return payment;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("Error retrieving payment: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getAll() throws DBException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("PaymentID"));
                payment.setOrderId(rs.getInt("OrderID"));
                payment.setCustomerId(rs.getInt("CustomerID"));
                payment.setPaymentMethod(rs.getString("PaymentMethod"));
                payment.setPaymentAmount(rs.getDouble("PaymentAmount"));
                payment.setPaymentDate(rs.getTimestamp("PaymentDate"));
                payment.setPaymentStatus(rs.getString("PaymentStatus"));
                payment.setTransactionId(rs.getString("TransactionID"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            throw new DBException("Error retrieving payments: " + e.getMessage());
        }
        return payments;
    }

    @Override
    public void update(Payment payment) throws DBException {
        String sql = "UPDATE payments SET OrderID = ?, CustomerID = ?, PaymentMethod = ?, PaymentAmount = ?, PaymentDate = ?, PaymentStatus = ?, TransactionID = ? WHERE PaymentID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, payment.getOrderId());
            stmt.setInt(2, payment.getCustomerId());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setDouble(4, payment.getPaymentAmount());
            stmt.setTimestamp(5, payment.getPaymentDate());
            stmt.setString(6, payment.getPaymentStatus());
            stmt.setString(7, payment.getTransactionId());
            stmt.setInt(8, payment.getPaymentId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error updating payment: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DBException {
        String sql = "DELETE FROM payments WHERE PaymentID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error deleting payment: " + e.getMessage());
        }
    }
}