package controllers;

import daos.imples.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import models.Order;
import models.User;

@WebServlet(name = "OrderListController", urlPatterns = {"/orders_list"})
public class OrderListController extends HttpServlet {
    private OrderDao orderDao;

    public OrderListController() {
        try {
            orderDao = new OrderDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/home_page");
            return;
        }

        User user = (User) session.getAttribute("user");
        String role = (String) session.getAttribute("role");

        if (!"Sale".equals(role)) {
            response.sendRedirect(request.getContextPath() + "/common/unauthorized.jsp");
            return;
        }

        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String status = request.getParameter("status");
        String search = request.getParameter("search");

        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int pageSize = (pageSizeParam != null) ? Integer.parseInt(pageSizeParam) : 10;
        sortField = (sortField != null) ? sortField : "created_at";
        sortOrder = (sortOrder != null) ? sortOrder : "ASC";

        int offset = (page - 1) * pageSize;

        List<Order> orders = orderDao.getOrders(sortField, sortOrder, fromDate, toDate, status, search, offset, pageSize);
        int totalOrders = orderDao.getOrderCount(fromDate, toDate, status, search);
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);

        request.setAttribute("orders", orders);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("status", status);
        request.setAttribute("search", search);

        request.getRequestDispatcher("/screens/OrderListSale.jsp").forward(request, response);
    }
}
