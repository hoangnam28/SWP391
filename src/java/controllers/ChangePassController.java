package controllers;

import daos.imples.UserDAO;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 * Servlet implementation class ChangePassController
 */
@WebServlet("/changepass")
public class ChangePassController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO dao;

    public void init() throws ServletException {
        try {
            dao = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Cannot initialize DAOs", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        if (userId == null) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Please log in to change your password.");
            response.getWriter().print(jsonResponse.toString());
            return;
        }

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        if (oldPassword == null || newPassword == null || confirmPassword == null ||
                oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "All fields are required.");
            response.getWriter().print(jsonResponse.toString());
            return;
        }

        boolean check = dao.currentPass(userId, oldPassword);
        if (check) {
            if (confirmPassword.equals(newPassword)) {
                boolean success = dao.changePass(userId, newPassword);
                if (success) {
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Password changed successfully.");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Failed to change password.");
                }
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "New password and confirmation do not match.");
            }
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Current password is incorrect.");
        }

        response.getWriter().print(jsonResponse.toString());
    }
}
