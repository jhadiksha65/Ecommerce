package ui;

import service.OrderService;
import model.Customer;
import model.Order;
import model.OrderItem;
import exception.DBException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class OrderFrame extends JFrame {
    private final Customer customer;
    private final OrderService orderService = new OrderService();
    private JList<Order> orderList;
    private DefaultListModel<Order> orderListModel;
    private JTextArea orderDetailsArea;

    public OrderFrame(Customer customer) {
        this.customer = customer;
        setTitle("E-commerce Orders");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        orderListModel = new DefaultListModel<>();
        orderList = new JList<>(orderListModel);
        panel.add(new JScrollPane(orderList), BorderLayout.WEST);

        orderDetailsArea = new JTextArea();
        orderDetailsArea.setEditable(false);
        panel.add(new JScrollPane(orderDetailsArea), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        panel.add(backButton, BorderLayout.SOUTH);

        add(panel);

        loadOrders();

        orderList.addListSelectionListener(e -> displayOrderDetails());
        backButton.addActionListener(e -> {
            dispose();
            new MainFrame(customer).setVisible(true);
        });
    }

    private void loadOrders() {
        try {
            List<Order> orders = orderService.getCustomerOrders(customer.getCustomerId());
            orderListModel.clear();
            for (Order order : orders) {
                orderListModel.addElement(order);
            }
        } catch (DBException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayOrderDetails() {
        Order selected = orderList.getSelectedValue();
        if (selected == null) return;
        try {
            List<OrderItem> items = orderService.getOrderItems(selected.getOrderId());
            StringBuilder details = new StringBuilder();
            details.append("Order ID: ").append(selected.getOrderId()).append("\n");
            details.append("Date: ").append(selected.getOrderDate()).append("\n");
            details.append("Status: ").append(selected.getOrderStatus()).append("\n");
            details.append("Payment Status: ").append(selected.getPaymentStatus()).append("\n");
            details.append("Items:\n");
            for (OrderItem item : items) {
                details.append("- Product ID: ").append(item.getProductId())
                       .append(", Quantity: ").append(item.getQuantity())
                       .append(", Price: $").append(item.getPrice()).append("\n");
            }
            orderDetailsArea.setText(details.toString());
        } catch (DBException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}