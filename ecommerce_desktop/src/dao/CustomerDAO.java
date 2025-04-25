package dao;

import model.Customer;
import exception.DBException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements DAO<Customer> {
    @Override
    public void add(Customer customer) throws DBException {
        String sql = "INSERT INTO customers (Name, EmailAddress, Password, PhoneNumber, ShippingAddress, BillingAddress, AccountStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmailAddress());
            stmt.setString(3, customer.getPassword());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getShippingAddress());
            stmt.setString(6, customer.getBillingAddress());
            stmt.setString(7, customer.getAccountStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error adding customer: " + e.getMessage());
        }
    }

    @Override
    public Customer getById(int id) throws DBException {
        String sql = "SELECT * FROM customers WHERE CustomerID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setName(rs.getString("Name"));
                customer.setEmailAddress(rs.getString("EmailAddress"));
                customer.setPassword(rs.getString("Password"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customer.setShippingAddress(rs.getString("ShippingAddress"));
                customer.setBillingAddress(rs.getString("BillingAddress"));
                customer.setAccountStatus(rs.getString("AccountStatus"));
                return customer;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("Error retrieving customer: " + e.getMessage());
        }
    }

    public Customer getByEmail(String email) throws DBException {
        String sql = "SELECT * FROM customers WHERE EmailAddress = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setName(rs.getString("Name"));
                customer.setEmailAddress(rs.getString("EmailAddress"));
                customer.setPassword(rs.getString("Password"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customer.setShippingAddress(rs.getString("ShippingAddress"));
                customer.setBillingAddress(rs.getString("BillingAddress"));
                customer.setAccountStatus(rs.getString("AccountStatus"));
                return customer;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("Error retrieving customer by email: " + e.getMessage());
        }
    }

    @Override
    public List<Customer> getAll() throws DBException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setName(rs.getString("Name"));
                customer.setEmailAddress(rs.getString("EmailAddress"));
                customer.setPassword(rs.getString("Password"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customer.setShippingAddress(rs.getString("ShippingAddress"));
                customer.setBillingAddress(rs.getString("BillingAddress"));
                customer.setAccountStatus(rs.getString("AccountStatus"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new DBException("Error retrieving customers: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public void update(Customer customer) throws DBException {
        String sql = "UPDATE customers SET Name = ?, EmailAddress = ?, Password = ?, PhoneNumber = ?, ShippingAddress = ?, BillingAddress = ?, AccountStatus = ? WHERE CustomerID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmailAddress());
            stmt.setString(3, customer.getPassword());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getShippingAddress());
            stmt.setString(6, customer.getBillingAddress());
            stmt.setString(7, customer.getAccountStatus());
            stmt.setInt(8, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error updating customer: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DBException {
        String sql = "DELETE FROM customers WHERE CustomerID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error deleting customer: " + e.getMessage());
        }
    }
}