// package controllers;
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
            } else if (!user.getStatus().equals("Active")) {
                request.setAttribute("error", "Account inactive!");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("user_id", user.getId());
                response.sendRedirect("home_page");
                return;
            }
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("isShow", true);
            request.getRequestDispatcher("/screens/HomePage.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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

