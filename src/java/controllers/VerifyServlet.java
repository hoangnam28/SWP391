/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import daos.imples.UserDAO;

/**
 * Servlet implementation class VerifyServlet
 */
@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        try {
            UserDAO userDAO = new UserDAO();
            boolean verified = userDAO.verifyUser(email);
            if (verified) {
                request.setAttribute("message", "Email verified successfully. You can now log in.");
                request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Invalid verification link or user already verified.");
                request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred during verification.");
            request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
        }
    }
}
