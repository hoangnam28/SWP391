package controllers;

import daos.imples.CategoryDao;
import daos.imples.PostDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Category;
import models.Post;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostDetailsController", urlPatterns = {"/post_details"})
public class PostDetailsController extends HttpServlet {

    private PostDao postDAO;
    private CategoryDao categoryDao;

    public void init() throws ServletException {
        try {
            postDAO = new PostDao();
            categoryDao = new CategoryDao();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Cannot initialize DAOs", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchQuery = request.getParameter("search");
        String postIdParam = request.getParameter("id");

        List<Category> categories = categoryDao.getAllCategories();
        request.setAttribute("categories", categories);
        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Perform search by title
            List<Post> searchResults = postDAO.searchPostsByTitle(searchQuery);
            if (!searchResults.isEmpty()) {
                // Forward to PostController to display search results
                request.setAttribute("searchQuery", searchQuery);
                request.getRequestDispatcher("/blogs?search=" + searchQuery).forward(request, response);
                return;
            } else {
                request.setAttribute("message", "No posts found with the title: " + searchQuery);
            }
        }
        if (postIdParam != null) {
            int postId = Integer.parseInt(postIdParam);
            Post post = postDAO.findById(postId);
            if (post != null) {
                Category category = categoryDao.findById(post.getCategoryId());
                request.setAttribute("post", post);
                request.setAttribute("updated_at", post.getUpdatedAt());
                request.setAttribute("category", category != null ? category.getName() : "Unknown");
            }
        }
        List<Post> latestPosts = postDAO.getLatestPosts(3);
        request.setAttribute("latestPosts", latestPosts);
        request.getRequestDispatcher("/screens/BlogDetails.jsp").forward(request, response);
    }
}
