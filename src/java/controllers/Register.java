/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.imples.UserDAO;
import models.User;
import utils.EmailSender;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = "Customer";
        String status = "Inactive";

        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Confirm password incorrect");
            request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            return;
        }

        // Kiểm tra định dạng email và số điện thoại di động
        if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            request.setAttribute("message", "Invalid email format");
            request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            return;
        }

        if (!mobile.matches("^0[0-9]{9}$")) {
            request.setAttribute("message", "Mobile number must start with 0 and be 10 digits long");
            request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            return;
        }

        if (password.length() != 8) {
            request.setAttribute("message", "Password must be exactly 8 characters long");
            request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            return;
        }

        User user = new User( fullName, gender, email, mobile, address, password, role, status, null);

        try {
            UserDAO userDAO = new UserDAO();
            boolean result = userDAO.registerUser(user);
            if (result) {
                String verificationLink = "http://localhost:8080/tech_store/verify?email=" + email;
                EmailSender.sendVerificationEmail(email, verificationLink);
                request.setAttribute("message", "Registration successful. Please check your email for verification.");
                request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "User already exists");
                request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred");
            request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
        } catch (MessagingException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Error while sending verification email");
            request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
        }
    }
}


