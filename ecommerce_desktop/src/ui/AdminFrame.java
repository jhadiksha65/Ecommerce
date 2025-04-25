package ui;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Customer;
import model.Order;
import model.Product;
import exception.DBException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private JList<Customer> customerList;
    private JList<Product> productList;
    private JList<Order> orderList;
    private DefaultListModel<Customer> customerListModel;
    private DefaultListModel<Product> productListModel;
    private DefaultListModel<Order> orderListModel;

    public AdminFrame() {
        setTitle("E-commerce Admin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel customerPanel = new JPanel(new BorderLayout());
        JPanel productPanel = new JPanel(new BorderLayout());
        JPanel orderPanel = new JPanel(new BorderLayout());

        customerListModel = new DefaultListModel<>();
        customerList = new JList<>(customerListModel);
        customerPanel.add(new JScrollPane(customerList), BorderLayout.CENTER);

        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        productPanel.add(new JScrollPane(productList), BorderLayout.CENTER);
        JPanel productButtonPanel = new JPanel(new FlowLayout());
        JButton addProductButton = new JButton("Add Product");
        JButton updateProductButton = new JButton("Update Product");
        productButtonPanel.add(addProductButton);
        productButtonPanel.add(updateProductButton);
        productPanel.add(productButtonPanel, BorderLayout.SOUTH);

        orderListModel = new DefaultListModel<>();
        orderList = new JList<>(orderListModel);
        orderPanel.add(new JScrollPane(orderList), BorderLayout.CENTER);

        tabbedPane.addTab("Customers", customerPanel);
        tabbedPane.addTab("Products", productPanel);
        tabbedPane.addTab("Orders", orderPanel);
        panel.add(tabbedPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton, BorderLayout.SOUTH);

        add(panel);

        loadData();

        addProductButton.addActionListener(e -> handleAddProduct());
        updateProductButton.addActionListener(e -> handleUpdateProduct());
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void loadData() {
        try {
            List<Customer> customers = customerDAO.getAll();
            customerListModel.clear();
            for (Customer customer : customers) {
                customerListModel.addElement(customer);
            }

            List<Product> products = productDAO.getAll();
            productListModel.clear();
            for (Product product : products) {
                productListModel.addElement(product);
            }

            List<Order> orders = orderDAO.getAll();
            orderListModel.clear();
            for (Order order : orders) {
                orderListModel.addElement(order);
            }
        } catch (DBException ex) {
            JOptionPane.showMessageDialog(this, ex.get10nPane(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddProduct() {
        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField skuField = new JTextField();
        Object[] fields = {
            "Name:", nameField,
            "Category:", categoryField,
            "Description:", descriptionField,
            "Price:", priceField,
            "Stock Quantity:", stockField,
            "SKU:", skuField
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Product product = new Product();
                product.setProductName(nameField.getText());
                product.setCategory(categoryField.getText());
                product.setDescription(descriptionField.getText());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setStockQuantity(Integer.parseInt(stockField.getText()));
                product.setSku(skuField.getText());
                product.setProductImages("http://example.com/images/default.jpg");
                product.setDimensions("N/A");
                product.setWeight(0.0);
                product.setRatingsAverage(0.0);
                productDAO.add(product);
                loadData();
                JOptionPane.showMessageDialog(this, "Product added!");
            } catch (DBException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleUpdateProduct() {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a product", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextField nameField = new JTextField(selected.getProductName());
        JTextField categoryField = new JTextField(selected.getCategory());
        JTextField descriptionField = new JTextField(selected.getDescription());
        JTextField priceField = new JTextField(String.valueOf(selected.getPrice()));
        JTextField stockField = new JTextField(String.valueOf(selected.getStockQuantity()));
        JTextField skuField = new JTextField(selected.getSku());
        Object[] fields = {
            "Name:", nameField,
            "Category:", categoryField,
            "Description:", descriptionField,
            "Price:", priceField,
            "Stock Quantity:", stockField,
            "SKU:", skuField
        };
        int result = JOptionPane.showConfirmDialog(this, fields, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                selected.setProductName(nameField.getText());
                selected.setCategory(categoryField.getText());
                selected.setDescription(descriptionField.getText());
                selected.setPrice(Double.parseDouble(priceField.getText()));
                selected.setStockQuantity(Integer.parseInt(stockField.getText()));
                selected.setSku(skuField.getText());
                productDAO.update(selected);
                loadData();
                JOptionPane.showMessageDialog(this, "Product updated!");
            } catch (DBException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}