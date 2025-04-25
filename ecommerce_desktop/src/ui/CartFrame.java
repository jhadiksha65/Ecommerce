package ui;

import service.CartService;
import service.OrderService;
import model.CartItem;
import model.Customer;
import exception.DBException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CartFrame extends JFrame {
    private final Customer customer;
    private final CartService cartService;
    private final OrderService orderService = new OrderService();
    private JList<CartItem> cartList;
    private DefaultListModel<CartItem> cartListModel;
    private JLabel totalLabel;

    public CartFrame(Customer customer, CartService cartService) {
        this.customer = customer;
        this.cartService = cartService;
        setTitle("E-commerce Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        panel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        totalLabel = new JLabel("Total: $0.00");
        panel.add(totalLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton removeButton = new JButton("Remove Item");
        JButton checkoutButton = new JButton("Checkout");
        JButton backButton = new JButton("Back");
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        loadCart();

        removeButton.addActionListener(e -> handleRemove());
        checkoutButton.addActionListener(e -> handleCheckout());
        backButton.addActionListener(e -> {
            dispose();
            new MainFrame(customer).setVisible(true);
        });
    }

    private void loadCart() {
        cartListModel.clear();
        List<CartItem> items = cartService.getCartItems();
        for (CartItem item : items) {
            cartListModel.addElement(item);
        }
        totalLabel.setText("Total: $" + String.format("%.2f", cartService.getTotal()));
    }

    private void handleRemove() {
        CartItem selected = cartList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select an item", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cartService.removeFromCart(selected.getProductId());
        loadCart();
    }

    private void handleCheckout() {
        try {
            String[] methods = {"credit card", "paypal", "digital wallet", "bank transfer"};
            String paymentMethod = (String) JOptionPane.showInputDialog(this, "Select payment method:", 
                "Payment", JOptionPane.QUESTION_MESSAGE, null, methods, methods[0]);
            if (paymentMethod != null) {
                orderService.placeOrder(customer.getCustomerId(), cartService.getCartItems(), 
                    customer.getShippingAddress(), customer.getBillingAddress(), paymentMethod);
                cartService.clearCart();
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
                dispose();
                new OrderFrame(customer).setVisible(true);
            }
        } catch (DBException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}