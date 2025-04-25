package dao;

import model.Product;
import exception.DBException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<Product> {
    @Override
    public void add(Product product) throws DBException {
        String sql = "INSERT INTO products (ProductName, Category, Description, Price, StockQuantity, SKU, ProductImages, Dimensions, Weight, RatingsAverage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getCategory());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setString(6, product.getSku());
            stmt.setString(7, product.getProductImages());
            stmt.setString(8, product.getDimensions());
            stmt.setDouble(9, product.getWeight());
            stmt.setDouble(10, product.getRatingsAverage());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error adding product: " + e.getMessage());
        }
    }

    @Override
    public Product getById(int id) throws DBException {
        String sql = "SELECT * FROM products WHERE ProductID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategory(rs.getString("Category"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStockQuantity(rs.getInt("StockQuantity"));
                product.setSku(rs.getString("SKU"));
                product.setProductImages(rs.getString("ProductImages"));
                product.setDimensions(rs.getString("Dimensions"));
                product.setWeight(rs.getDouble("Weight"));
                product.setRatingsAverage(rs.getDouble("RatingsAverage"));
                return product;
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("Error retrieving product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAll() throws DBException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setCategory(rs.getString("Category"));
                product.setDescription(rs.getString("Description"));
                product.setPrice(rs.getDouble("Price"));
                product.setStockQuantity(rs.getInt("StockQuantity"));
                product.setSku(rs.getString("SKU"));
                product.setProductImages(rs.getString("ProductImages"));
                product.setDimensions(rs.getString("Dimensions"));
                product.setWeight(rs.getDouble("Weight"));
                product.setRatingsAverage(rs.getDouble("RatingsAverage"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DBException("Error retrieving products: " + e.getMessage());
        }
        return products;
    }

    @Override
    public void update(Product product) throws DBException {
        String sql = "UPDATE products SET ProductName = ?, Category = ?, Description = ?, Price = ?, StockQuantity = ?, SKU = ?, ProductImages = ?, Dimensions = ?, Weight = ?, RatingsAverage = ? WHERE ProductID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getCategory());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setString(6, product.getSku());
            stmt.setString(7, product.getProductImages());
            stmt.setString(8, product.getDimensions());
            stmt.setDouble(9, product.getWeight());
            stmt.setDouble(10, product.getRatingsAverage());
            stmt.setInt(11, product.getProductId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error updating product: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws DBException {
        String sql = "DELETE FROM products WHERE ProductID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error deleting product: " + e.getMessage());
        }
    }
}}
