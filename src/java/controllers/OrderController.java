package controllers;

import daos.imples.OrderDao;
import daos.imples.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Order;
import models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "OrderController", urlPatterns = {"/orderDetails"})
public class OrderController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDao orderDao;
    private UserDAO userDAO;

    public OrderController() {
        try {
            orderDao = new OrderDao();
            userDAO = new UserDAO();
        } catch (SQLException e) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null || (!user.getRole().equals("Sale"))) {
            response.sendRedirect(request.getContextPath() + "/common/unauthorized.jsp");
            return;
        }

        String orderIdStr = request.getParameter("id");
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/common/error.jsp");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderDao.findById(orderId);
            if (order == null) {
                response.sendRedirect(request.getContextPath() + "/common/error.jsp");
                return;
            }

            List<User> saleUsers = userDAO.findUsersByRole("Sale");
            request.setAttribute("saleUsers", saleUsers);
            request.setAttribute("order", order);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/screens/OrderDetailSale.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException | SQLException e) {
            response.sendRedirect(request.getContextPath() + "/common/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null || (!user.getRole().equals("Sale"))) {
            response.sendRedirect(request.getContextPath() + "/common/unauthorized.jsp");
            return;
        }

        String orderIdStr = request.getParameter("id");
        String status = request.getParameter("status");
        String notes = request.getParameter("notes");
        String saleUserIdStr = request.getParameter("saleUserId");

        if (orderIdStr == null || orderIdStr.isEmpty() || status == null || status.isEmpty() || saleUserIdStr == null || saleUserIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/common/error.jsp");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            int saleUserId = Integer.parseInt(saleUserIdStr);
            Order order = orderDao.findById(orderId);

            if (order == null) {
                response.sendRedirect(request.getContextPath() + "/common/error.jsp");
                return;
            }

            order.setStatus(status);
            order.setNotes(notes);
            order.setSaleId(saleUserId);
            orderDao.update(order);

            response.sendRedirect(request.getContextPath() + "/orderDetails?id=" + orderId);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/common/error.jsp");
        }
    }
}
