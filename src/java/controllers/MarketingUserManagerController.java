package controllers;

import daos.imples.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MarketingUserManagerController", urlPatterns = {"/customers_list", "/update_status"})
public class MarketingUserManagerController extends HttpServlet {

    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        if (!"Marketing".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/common/unauthorized.jsp");
            return;
        }

        String search = request.getParameter("search");
        String status = request.getParameter("status");
        String sort = request.getParameter("sort") != null ? request.getParameter("sort") : "full_name";
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 10;

        List<User> customers = userDAO.getUsersByRole("Customer", search, status, sort, page, pageSize);
        int noOfRecords = userDAO.getTotalRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / pageSize);

        request.setAttribute("customers", customers);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/screens/MarketingCustomersList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addCustomer(request, response);
        } else if ("update_status".equals(action)) {
            updateStatus(request, response);
        } else {
            doGet(request, response);
        }
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullName = request.getParameter("full_name");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
                String address = request.getParameter("address");
        String password = request.getParameter("password");  // Assuming you get a password from the form
        String status = request.getParameter("status");
        String avatar = request.getParameter("avatar");

        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setGender(gender);
        newUser.setEmail(email);
        newUser.setMobile(mobile);
        newUser.setAddress(address);
        newUser.setPassword(password);  // You should hash the password here
        newUser.setRole("Customer");
        newUser.setStatus(status);
        newUser.setAvatar(avatar);

        userDAO.insert(newUser);
        response.sendRedirect("customers_list");
    }

    private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");

        User user = userDAO.findById(id);
        if (user != null) {
            user.setStatus(status);
            userDAO.update(user);
            response.getWriter().write("{\"success\": true}");
        } else {
            response.getWriter().write("{\"success\": false}");
        }
    }
}
