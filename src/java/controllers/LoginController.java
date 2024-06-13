package controllers;

import daos.imples.AccountDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            AccountDao accountDao = new AccountDao();
            User user = accountDao.login(email, password);
            if (user == null) {
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/screens/HomePage.jsp").forward(request, response);
                return;
            } else if (!user.getStatus().equals("Active")) {
                request.setAttribute("error", "Account inactive!");
                request.getRequestDispatcher("/screens/HomePage.jsp").forward(request, response);
                return;
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("user_id", user.getId());
                session.setAttribute("role", user.getRole()); // Ensure the role is set

                // Log session attributes
                System.out.println("User: " + user);
                System.out.println("User Role: " + user.getRole());
                System.out.println("Session ID: " + session.getId());
                Enumeration<String> attributeNames = session.getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    String attributeName = attributeNames.nextElement();
                    System.out.println("Session attribute: " + attributeName + " = " + session.getAttribute(attributeName));
                }

                // Redirect based on user role
                if ("Marketing".equals(user.getRole())) {
                    response.sendRedirect("./customers_list");
                } else {
                    response.sendRedirect("./home_page");
                }
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("/screens/HomePage.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}



