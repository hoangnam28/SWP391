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
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            response.sendRedirect("./login");
            return;
        }
        
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        boolean check = dao.currentPass(userId, oldPassword);
        if (check) {
            if (confirmPassword.equals(newPassword)) {
                boolean success = dao.changePass(userId, newPassword);
                if (success) {
                    request.setAttribute("success", "Password changed successfully.");
                    request.getRequestDispatcher("./home_page").forward(request, response);
                } else {
                    request.setAttribute("error", "Failed to change password.");
                    request.getRequestDispatcher("./home_page").forward(request, response);
                }
            } else {
                request.setAttribute("error", "New password and confirmation do not match.");
                request.getRequestDispatcher("./home_page").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Current password is incorrect.");
            request.getRequestDispatcher("./home_page").forward(request, response);
        }
    }
}
