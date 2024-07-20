/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import daos.imples.CartDAO;
import daos.imples.ProductDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Cart;
import models.CartItem;
import models.Products;
import models.User;

/**
 *
 * @author NgocHung_ighoorbeos
 */

public class AddtoCart extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddtoCart</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddtoCart at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.addToCart(request, response);
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(user == null) {
                response.sendRedirect(request.getContextPath() + "/?error=You must login");
                return;
            }
            int idUser = user.getId();
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            ProductDao productDao = new ProductDao();
            CartDAO cartDao = new CartDAO();
            Products productAdd = productDao.findById(productId);
            if (productAdd != null) {
                CartItem cartItemExist = cartDao.getCartItemsWithDetails(productId, idUser);
                Cart cartExist = cartDao.getCartWithByUser(idUser);
                int idCart = 0;
                double itemCost = productAdd.getSalePrice() > 0 ? productAdd.getSalePrice() : productAdd.getOriginalPrice();
                double totalCost = productAdd.getSalePrice() > 0 ? productAdd.getSalePrice() * quantity : productAdd.getOriginalPrice() * quantity;
                if (cartItemExist != null) {
                    if (cartExist == null) {
                        Cart newCart = new Cart(idUser, totalCost);
                        idCart = cartDao.addCart(newCart);
                    } else {
                        cartExist.setTotalCost(totalCost + cartExist.getTotalCost());
                        cartDao.updateCart(cartExist);
                        idCart = cartExist.getId();
                    }
                    int newQuantity = cartItemExist.getQuantity() + quantity;
                    if (newQuantity <= productAdd.getStock()) {
                        cartItemExist.setQuantity(newQuantity);
                        cartItemExist.setTotalPrice(cartItemExist.getTotalPrice() + totalCost);
                        cartDao.updateCartItem(cartItemExist);
                    } else {
                        response.sendRedirect("CartDetailController?error=Limit stock");
                    }
                    response.sendRedirect("CartDetailController?success=Add to cart successfully");
                } else {
                    if (cartExist == null) {
                        Cart newCart = new Cart(idUser, totalCost);
                        idCart = cartDao.addCart(newCart);
                    } else {
                        cartExist.setTotalCost(totalCost + cartExist.getTotalCost());
                        cartDao.updateCart(cartExist);
                        idCart = cartExist.getId();
                    }
                    int newQuantity = quantity;
                    if (newQuantity <= productAdd.getStock()) {
                        CartItem cartItemNoExist = new CartItem(idCart, productId, quantity, itemCost);
                        cartItemNoExist.setTotalPrice(totalCost);
                        cartDao.addCartItem(cartItemNoExist);
                    } else {
                        response.sendRedirect("CartDetailController?error=Limit stock");
                    }
                    response.sendRedirect("CartDetailController?success=Add to cart successfully");
                }
            } else {
                response.sendRedirect("CartDetailController?Error=Can not found product");
            }
        } catch (Exception e) {
            System.out.println("Add to cart fail: " + e);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
