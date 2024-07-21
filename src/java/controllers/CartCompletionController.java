package controllers;

import daos.imples.CartDetailDAO;
import daos.imples.OrderDao;
import daos.imples.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import models.CartItem;
import models.Order;
import models.User;
import org.json.JSONObject;
import utils.EmailSender;

@WebServlet(name = "CartCompletionController", urlPatterns = {"/cart_completion"})
public class CartCompletionController extends HttpServlet {

    private OrderDao orderDao;
    private ProductDao productDao;
    private EmailSender emailSender;
    private CartDetailDAO cartDetailDAO;

    @Override
    public void init() throws ServletException {
        try {
            orderDao = new OrderDao();
            productDao = new ProductDao();
            cartDetailDAO = new CartDetailDAO();
        } catch (SQLException ex) {
            Logger.getLogger(CartCompletionController.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
        emailSender = new EmailSender();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/?error=You must login");
            return;
        }

        int idUser = user.getId();
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        int cartId = cartItems.get(0).getCartId();

        double totalCost = 0.0;
        for (CartItem cartItem : cartItems) {
            totalCost += cartItem.getTotalPrice();
        }

        for (CartItem cartItem : cartItems) {
            int productId = cartItem.getProductId();
            int quantity = cartItem.getQuantity();
            productDao.updateProductQuantity(productId, -quantity); // Adjust quantity correctly
        }

        Order order = new Order();
        order.setUserId(idUser);
        order.setReceiverName(user.getFullName());
        order.setReceiverGender(user.getGender());
        order.setReceiverEmail(user.getEmail());
        order.setReceiverMobile(user.getMobile());
        order.setReceiverAddress(user.getAddress());
        order.setTotalCost(BigDecimal.valueOf(totalCost));
        order.setStatus("Submitted"); // Set a default status
        orderDao.createOrder(order); // Assuming createOrder inserts the order and sets its ID

        int orderId = order.getId(); // Retrieve the generated order ID

        for (CartItem cartItem : cartItems) {
            Integer productId = cartItem.getProductId();
            Integer quantity = cartItem.getQuantity();
            Double totalPrice = cartItem.getTotalPrice();

            if (productId == null || quantity == null || totalPrice == null) {
                System.out.println("Null values in cartItem: " + cartItem);
                continue;
            }

            System.out.println("Creating order item with orderId: " + orderId + ", productId: " + productId + ", quantity: " + quantity + ", totalPrice: " + totalPrice);
            orderDao.createOrderItem(orderId, productId, quantity, totalPrice);
        }

        Order detailedOrder = orderDao.findByIdOrder(orderId);
        detailedOrder.setReceiverName(user.getFullName());
        detailedOrder.setReceiverEmail(user.getEmail());
        detailedOrder.setReceiverMobile(user.getMobile());
        detailedOrder.setReceiverGender(user.getGender());
        detailedOrder.setReceiverAddress(user.getAddress());

        String paymentMethod = request.getParameter("paymentMethod");
        String email = user.getEmail();
        String subject = "Order Confirmation";
        String body = "Thank you for your order.";

        JSONObject qrData = null;
        String qrCodeUrl = null;

        if ("payOnDelivery".equals(paymentMethod)) {
            detailedOrder.setStatus("Submitted");
            body += "\n\nYour order will be delivered to your address. Please pay on delivery.";
            request.setAttribute("order", detailedOrder);
            orderDao.updateOrderStatus(orderId, "Submitted");
        } else if ("qrPayment".equals(paymentMethod)) {
            detailedOrder.setStatus("Submitted");
            body += "\n\nPlease use the following QR code to complete your payment.";
            body += "\n\nPayment Information:\nBank Account: 42710000772390"; // Add actual payment info here

            qrData = new JSONObject();
            qrData.put("accountNo", "42710000772390");
            qrData.put("accountName", "Nguyen Hoang Nam");
            qrData.put("acqId", "970415");
            qrData.put("addInfo", "Payment for Order ID: " + orderId);
            qrData.put("amount", totalCost);
            qrData.put("template", "compact2");

            qrCodeUrl = generateQrCode(qrData);

            body += "\n\nQR Code: " + qrCodeUrl;
            orderDao.updateOrderStatus(orderId, "Submitted");
        }

        try {
            EmailSender.sendEmailToConfirmOrder(user.getEmail());
        } catch (MessagingException ex) {
            Logger.getLogger(CartCompletionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Xóa sản phẩm khỏi giỏ hàng
        for (CartItem cartItem : cartItems) {
            int productId = cartItem.getProductId();
            try {
                System.out.println("Deleting cart item: cartId=" + cartId + ", productId=" + productId);
                cartDetailDAO.deleteCartItemByCartID(cartId, productId);
            } catch (SQLException ex) {
                Logger.getLogger(CartCompletionController.class.getName()).log(Level.SEVERE, null, ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
                return;
            }
        }

        session.removeAttribute("cartItems"); // Clear the cart after order is placed

        request.setAttribute("order", detailedOrder);
        request.setAttribute("qrCodeUrl", qrCodeUrl);
        request.getRequestDispatcher("/screens/CartCompletion.jsp").forward(request, response);
    }

    private String generateQrCode(JSONObject qrData) {
        String urlString = "https://api.vietqr.io/v2/generate";
        String clientId = "35a757de-2016-40d4-a450-ba951b25d622";
        String apiKey = "4940af21-166f-4629-aaf8-ee002d6cd6ba";

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("x-client-id", clientId);
            connection.setRequestProperty("x-api-key", apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = qrData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    JSONObject responseJson = new JSONObject(response.toString());
                    String qrCodeUrl = responseJson.getString("imgURL");
                    return qrCodeUrl;
                }
            } else {
                System.out.println("Failed to generate QR code: " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
