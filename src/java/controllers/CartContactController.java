package controllers;

import daos.imples.CartContactDAO;
import daos.imples.CartDAO;
import daos.imples.CartDetailDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Cart;
import models.CartItem;
import models.User;

public class CartContactController extends HttpServlet {

    private CartDetailDAO cartDetailDAO;
    private CartContactDAO cartcontactdao;
    private CartDAO cartdao;

    @Override
    public void init() throws ServletException {
        try {
            cartDetailDAO = new CartDetailDAO();
            cartcontactdao = new CartContactDAO();
            cartdao = new CartDAO();
        } catch (SQLException ex) {
            Logger.getLogger(CartContactController.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/?error=You must login");
            return;
        }

        int idUser = user.getId();
        List<User> users = cartcontactdao.findAllByUserID(idUser);
        double totalCost = 0.0;

        List<CartItem> cartItems = null;
        List<Cart> cartlist = null;
        cartItems = cartDetailDAO.listAllByUser(idUser);

        for (CartItem cartItem : cartItems) {
            totalCost += cartItem.getTotalPrice();
        }

        // Set cartItems in session
        session.setAttribute("cartItems", cartItems);

        if (users != null && !users.isEmpty()) {
            request.setAttribute("user", users.get(0));
        } else {
            request.setAttribute("user", null);
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalCost", totalCost);
        request.getRequestDispatcher("screens/CartContact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implement POST logic if needed
    }

    @Override
    public String getServletInfo() {
        return "CartContactController handles displaying the cart and its items.";
    }
}
