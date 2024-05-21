package controllers;

/**
 *
 * @author YOUR NAME
 */
import daos.imples.PostDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostController", urlPatterns = {"/blogs"})
public class PostController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PostDao postDAO;

    public void init() throws ServletException {
        try {
            postDAO = new PostDao();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Cannot initialize PostDAO", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));

        List<Post> list = postDAO.selectAllPostsPaginated((page - 1) * recordsPerPage, recordsPerPage);
        List<String> categories = postDAO.getAllCategories(); // Assuming you have a method to retrieve all categories from the database
            request.setAttribute("categories", categories);
        int noOfRecords = postDAO.getTotalPosts();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("postList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        request.getRequestDispatcher("/screens/Blog.jsp").forward(request, response);
    }
}


