package ui;

import service.AuthService;
import model.Customer;
import exception.DBException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationFrame extends JFrame {
    private JTextField nameField, emailField, phoneField, shippingField, billingField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private final AuthService authService = new AuthService();

    public RegistrationFrame() {
        setTitle("E-commerce Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);
        panel.add(new JLabel("Shipping Address:"));
        shippingField = new JTextField();
        panel.add(shippingField);
        panel.add(new JLabel("Billing Address:"));
        billingField = new JTextField();
        panel.add(billingField);

        registerButton = new JButton("Register");
        backButton = new JButton("Back");
        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void handleRegister() {
        try {
            Customer customer = new Customer();
            customer.setName(nameField.getText());
            customer.setEmailAddress(emailField.getText());
            customer.setPassword(new String(passwordField.getPassword()));
            customer.setPhoneNumber(phoneField.getText());
            customer.setShippingAddress(shippingField.getText());
            customer.setBillingAddress(billingField.getText());
            customer.setAccountStatus("active");
            authService.register(customer);
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose();
            new LoginFrame().setVisible(true);
        } catch (DBException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}