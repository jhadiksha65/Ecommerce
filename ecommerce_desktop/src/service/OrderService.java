package service;

import model.Order;
import model.OrderItem;
import model.Payment;
import model.CartItem;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.PaymentDAO;
import dao.ProductDAO;
import exception.DBException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final ProductDAO productDAO = new ProductDAO();

    public void placeOrder(int customerId, List<CartItem> cartItems, String shippingAddress, String billingAddress, String paymentMethod) throws DBException {
        if (cartItems.isEmpty()) {
            throw new DBException("Cart is empty");
        }

        // Create order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus("pending");
        order.setPaymentStatus("unpaid");
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setTrackingNumber(null);
        orderDAO.add(order);

        // Add order items and update stock
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(order.getOrderId(), cartItem.getProductId(), cartItem.getQuantity(), cartItem.getPrice());
            orderItemDAO.add(orderItem);
            model.Product product = productDAO.getById(cartItem.getProductId());
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productDAO.update(product);
        }

        // Create payment
        double total = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        Payment payment = new Payment();
        payment.setOrderId(order.getOrderId());
        payment.setCustomerId(customerId);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentAmount(total);
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        payment.setPaymentStatus("successful");
        payment.setTransactionId("TXN" + UUID.randomUUID().toString().substring(0, 8));
        paymentDAO.add(payment);

        // Update order status
        order.setOrderStatus("confirmed");
        order.setPaymentStatus("paid");
        orderDAO.update(order);
    }

    public List<Order> getCustomerOrders(int customerId) throws DBException {
        return orderDAO.getByCustomerId(customerId);
    }

    public List<OrderItem> getOrderItems(int orderId) throws DBException {
        return orderItemDAO.getByOrderId(orderId);
    }
}