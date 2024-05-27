package controllers;

import daos.imples.PostDao;
import models.Post;
import models.Slider;
import daos.imples.SliderDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeController", urlPatterns = {"/home_page"})
public class HomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        List<Slider> sliders = null;
        List<Post> latestPosts = null;
        List<Post> hostPosts = null;

        try {
            SliderDao sliderDao = new SliderDao();
            sliders = sliderDao.findAll();

            PostDao postDao = new PostDao();
            latestPosts = postDao.getLatestPosts(3); // Lấy 6 bài đăng mới nhất
            hostPosts = postDao.getHostPosts(4);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        // Set attributes for JSP
        request.setAttribute("sliders", sliders);
        request.setAttribute("latestPosts", latestPosts);
        request.setAttribute("hostPosts", hostPosts);

        // Forward to homepage.jsp
        request.getRequestDispatcher("screens/HomePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp); // Xử lý yêu cầu POST
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
