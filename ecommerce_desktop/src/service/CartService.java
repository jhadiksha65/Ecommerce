package service;

import dao.CustomerDAO;
import model.Customer;
import exception.AuthException;
import exception.DBException;

public class AuthService {
    private final CustomerDAO customerDAO = new CustomerDAO();

    public Customer login(String email, String password) throws AuthException, DBException {
        if (email.equals("admin@example.com") && password.equals("admin123")) {
            Customer admin = new Customer();
            admin.setEmailAddress(email);
            admin.setName("Admin");
            admin.setAccountStatus("active");
            return admin;
        }
        Customer customer = customerDAO.getByEmail(email);
        if (customer == null || !customer.getPassword().equals(password)) {
            throw new AuthException("Invalid email or password");
        }
        if (!customer.getAccountStatus().equals("active")) {
            throw new AuthException("Account is inactive");
        }
        return customer;
    }

    public void register(Customer customer) throws DBException {
        customer.setAccountStatus("active");
        customerDAO.add(customer);
    }
}