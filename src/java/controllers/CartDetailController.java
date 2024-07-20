package controllers;

import daos.imples.CartDAO;
import daos.imples.CartDetailDAO;
import daos.imples.ProductDao;
import models.CartItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Cart;
import models.Products;
import models.User;

public class CartDetailController extends HttpServlet {

    private CartDetailDAO cartDetailDAO;
    private ProductDao productDAO;
    private CartDAO cartdao;

    @Override
    public void init() throws ServletException {
        try {
            cartDetailDAO = new CartDetailDAO();
            productDAO = new ProductDao();
            cartdao = new CartDAO();
        } catch (SQLException ex) {
            Logger.getLogger(CartDetailController.class.getName()).log(Level.SEVERE, null, ex);
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
        List<CartItem> cartItems = null;
        Cart cartlist = null;
        double totalCost = 0.0;

        try {
            cartlist = cartdao.getAllCartByUser(idUser);
            cartItems = cartDetailDAO.listAllByUser(idUser);

            for (CartItem cartItem : cartItems) {
                totalCost += cartItem.getTotalPrice();
            }
        } catch (SQLException e) {
            Logger.getLogger(CartDetailController.class.getName()).log(Level.SEVERE, null, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            return;
        }

        // Set cartItems in session
        session.setAttribute("cartItems", cartItems);

        request.setAttribute("cart", cartlist);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalCost", totalCost);
        request.getRequestDispatcher("/screens/CartDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/?error=You must login");
            return;
        }
        if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("cartItemIdDelete"));
            try {
                cartDetailDAO.deleteCartItemByProductId(productId);
                response.sendRedirect("CartDetailController");
            } catch (SQLException ex) {
                response.getWriter().write("error");
                return;
            }
        } else if ("update".equals(action)) {

            try {
                ProductDao productDao = new ProductDao();
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

                for (CartItem cartItem : cartDetailDAO.listAllByUser(user.getId())) {
                    System.out.println(cartItemId + " " + cartItem.getId());
                    if (cartItem.getId() == cartItemId) {
                        Products pro = productDao.findById(cartItem.getProductId());
                        int newQuantity = Integer.parseInt(request.getParameter("quantity"));
                        if (newQuantity > pro.getStock()) {
                            response.getWriter().write("quantity");
                            return;
                        }
                        try {
                            cartDetailDAO.updateCartItemQuantity(cartItem.getId(), newQuantity);
                            response.getWriter().write("success");
                        } catch (SQLException ex) {
                            System.out.println("Ex: " + ex);
                            response.getWriter().write("error");
                            return;
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex);
                response.getWriter().write("error");
            }
        }
    }
}
