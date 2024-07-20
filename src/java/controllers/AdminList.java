package controllers;

import daos.imples.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

/**
 *
 * @author 84397
 */
@WebServlet(name = "AdminList", urlPatterns = {"/admin_list"})
@MultipartConfig
public class AdminList extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        try {
            userDAO = new UserDAO();
        } catch (SQLException ex) {
            Logger.getLogger(AdminList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = (String) request.getSession().getAttribute("role");

        if (!"Admin".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/common/unauthorized.jsp");
            return;
        }
        try {
            // Handle search, status, sort, and pagination parameters
            String search = request.getParameter("search");
            String status = request.getParameter("status");
            String sort = request.getParameter("sort");
            int page = 1;
            int recordsPerPage = 5;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            // Fetch users based on search, status, sort, and pagination
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.findAll1(search, status, sort, (page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecords = userDAO.getNoOfRecords();
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute("users", users);
            request.setAttribute("search", search);
            request.setAttribute("status", status);
            request.setAttribute("sort", sort);
            request.setAttribute("noOfPages", noOfPages);
            request.setAttribute("currentPage", page);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/screens/admin_list.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addUser(request, response);
                    break;
                case "update_status":
                    updateStatus(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String fullName = request.getParameter("full_name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String password = request.getParameter("password");
        if (userDAO.checkUserExists(email)) {
            request.setAttribute("errorMsg", "Email already exists");
            response.sendRedirect("admin_list");
            return;
        }
        User user = new User();
        user.setFullName(fullName);
        user.setGender(gender);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setAddress(address);
        user.setRole(role);
        user.setStatus(status);
        user.setPassword(password);

        if (userDAO.registerUser(user)) {
            response.sendRedirect("admin_list");
        } else {
            response.getWriter().write("failure");
        }
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String userIdParam = request.getParameter("id");
            String status = request.getParameter("status");

            if (userIdParam == null || status == null) {
                response.getWriter().write("{\"success\": false}");
                return;
            }

            int userId = Integer.parseInt(userIdParam);

            boolean updated = userDAO.updateStatus(userId, status);
            if (updated) {
                response.getWriter().write("{\"success\": true}");
            } else {
                response.getWriter().write("{\"success\": false}");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false}");
        }
    }
}
