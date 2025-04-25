package ui;

import service.AuthService;
import model.Customer;
import exception.AuthException;
import exception.DBException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("E-commerce Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> {
            dispose();
            new RegistrationFrame().setVisible(true);
        });
    }

    private void handleLogin() {
        try {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            Customer customer = authService.login(email, password);
            dispose();
            if (email.equals("admin@example.com")) {
                new AdminFrame().setVisible(true);
            } else {
                new MainFrame(customer).setVisible(true);
            }
        } catch (AuthException | DBException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}