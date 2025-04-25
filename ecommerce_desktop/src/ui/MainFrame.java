package ui;

import service.CartService;
import service.OrderService;
import model.Customer;
import model.Product;
import dao.ProductDAO;
import exception.DBException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final Customer customer;
    private final CartService cartService = new CartService();
    private final ProductDAO productDAO = new ProductDAO();
    private JList<Product> productList;
    private DefaultListModel<Product> productListModel;

    public MainFrame(Customer customer) {
        this.customer = customer;
        setTitle("E-commerce Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        panel.add(new JScrollPane(productList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addToCartButton = new JButton("Add to Cart");
        JButton viewCartButton = new JButton("View Cart");
        JButton viewOrdersButton = new JButton("View Orders");
        JButton logoutButton = new JButton("Logout");
        buttonPanel.add(addToCartButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        loadProducts();

        addToCartButton.addActionListener(e -> handleAddToCart());
        viewCartButton.addActionListener(e -> {
            dispose();
            new CartFrame(customer, cartService).setVisible(true);
        });
        viewOrdersButton.addActionListener(e -> {
            dispose();
            new OrderFrame(customer).setVisible(true);
        });
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void loadProducts() {
        try {
            List<Product> products = productDAO.getAll();
            productListModel.clear();
            for (Product product : products) {
                productListModel.addElement(product);
            }
        } catch (DBException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleAddToCart() {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a product", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:");
        try {
            int quantity = Integer.parseInt(quantityStr);
            cartService.addToCart(selected.getProductId(), quantity);
            JOptionPane.showMessageDialog(this, "Added to cart!");
        } catch (NumberFormatException | DBException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity or error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}