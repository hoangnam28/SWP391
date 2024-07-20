package controllers;

import daos.imples.UserDAO;
import models.User;
import utils.EmailSender;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/register")
public class Register extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(Register.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = "Customer";
        String status = "Inactive";

        logger.info("Received registration request: " + email);

        try {
            if (!password.equals(confirmPassword)) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Confirm password incorrect");
            } else if (!email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Invalid email format");
            } else if (!mobile.matches("^0[0-9]{9}$")) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Mobile number must start with 0 and be 10 digits long");
            } else if (password.length() != 8) {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Password must be exactly 8 characters long");
            } else {
                User user = new User(fullName, gender, email, mobile, address, password, role, status, null);
                UserDAO userDAO = new UserDAO();
                boolean result = userDAO.registerUser(user);

                if (result) {
                    String verificationLink = "http://localhost:8080/tech_store/verify?email=" + email;
                    EmailSender.sendVerificationEmail(email, verificationLink);
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Registration successful. Please check your email for verification.");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "User already exists");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Exception during registration", e);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "An error occurred: " + e.getMessage());
        } catch (MessagingException ex) {
            logger.log(Level.SEVERE, "Messaging Exception during registration", ex);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error while sending verification email: " + ex.getMessage());
        } finally {
            out.print(jsonResponse.toString());
            out.flush();
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
        return "Register servlet";
    }
}
