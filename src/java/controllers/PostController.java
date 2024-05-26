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

@WebServlet(name = "PostController", urlPatterns = {"/blogs"})
public class PostController extends HttpServlet {

    private static final long serialVersionUID = 1L;
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
        int page = 1;
        int recordsPerPage = 10;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        String categoryIdParam = request.getParameter("categoryId");
        String searchQuery = request.getParameter("search");

        List<Post> list;
        int noOfRecords;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            // Perform search by title
            list = postDAO.searchPostsByTitle(searchQuery);
            noOfRecords = list.size(); // Update the number of records to the size of search results
            request.setAttribute("searchQuery", searchQuery);
        } else if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            int categoryId = Integer.parseInt(categoryIdParam);
            list = postDAO.selectPostsByCategoryPaginated(categoryId, (page - 1) * recordsPerPage, recordsPerPage);
            noOfRecords = postDAO.getTotalPostsByCategory(categoryId);
            request.setAttribute("selectedCategoryId", categoryId);
        } else {
            list = postDAO.selectAllPostsPaginated((page - 1) * recordsPerPage, recordsPerPage);
            noOfRecords = postDAO.getTotalPosts();
        }

        List<Category> categories = categoryDao.getAllCategories();
        request.setAttribute("categories", categories);
        List<Post> latestPosts = postDAO.getLatestPosts(3);

        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        request.setAttribute("postList", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("latestPosts", latestPosts);

        request.getRequestDispatcher("/screens/Blog.jsp").forward(request, response);
    }

}
