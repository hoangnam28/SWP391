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
    // Assuming CategoryDao is required for other operations, otherwise it can be removed.

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
        String email = "";
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        boolean check = dao.currentPass(email, oldPassword);
        if (check) {
            if (confirmPassword.equals(newPassword)) {
                boolean success = dao.changePass(email, newPassword);
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/home_page");
                } else {
                    response.sendRedirect(request.getContextPath() + "/home_page");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/home_page");
            }
        } else {
           response.sendRedirect(request.getContextPath() + "/home_page");
        }
    }
}
