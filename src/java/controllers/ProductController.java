package controllers;

import daos.imples.ProductDao;
import daos.imples.CategoryDao;
import models.Products;
import models.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/products")
public class ProductController extends HttpServlet {

    private ProductDao daoProductController;
    private CategoryDao categoryDao;
    private static final int RECORDS_PER_PAGE = 8;

    @Override
    public void init() throws ServletException {
        try {
            daoProductController = new ProductDao();
            categoryDao = new CategoryDao();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Cannot initialize DAOs", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchByDescription = req.getParameter("search");
        String category = req.getParameter("category");
        String sortPrice = req.getParameter("sort");
        String searchByTitle = req.getParameter("title");
        int page = 1;
        if(req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        int startIndex = (page - 1) * RECORDS_PER_PAGE;

        try {
            // Fetch all categories
            List<Category> categories = categoryDao.findAll();
            req.setAttribute("categories", categories);

            // Fetch products based on category with pagination
            List<Products> allProducts;
            int numberOfPages;
            if (category != null && !category.isEmpty()) {
                allProducts = daoProductController.findByCategoryWithPagination(Integer.parseInt(category), startIndex, RECORDS_PER_PAGE);
                int totalProducts = daoProductController.countProductsByCategory(Integer.parseInt(category));
                numberOfPages = (int) Math.ceil(totalProducts * 1.0 / RECORDS_PER_PAGE);
            } else {
                allProducts = daoProductController.findAllWithPagination(startIndex, RECORDS_PER_PAGE);
                int totalProducts = daoProductController.countAllProducts();
                numberOfPages = (int) Math.ceil(totalProducts * 1.0 / RECORDS_PER_PAGE);
            }

            // Search products by title
            if (searchByTitle != null && !searchByTitle.isEmpty()) {
                allProducts = daoProductController.findByTitle(searchByTitle);
            }

            // Search products by description
            if (searchByDescription != null && !searchByDescription.isEmpty()) {
                allProducts = daoProductController.findByDescription(searchByDescription);
            }

            // Sort products by price
            if ("price_asc".equals(sortPrice)) {
                allProducts.sort(Comparator.comparingDouble(Products::getSalePrice));
            } else if ("price_desc".equals(sortPrice)) {
                allProducts.sort(Comparator.comparingDouble(Products::getSalePrice).reversed());
            }

            // Set attributes for request
            req.setAttribute("searchByDescription", searchByDescription);
            req.setAttribute("category", category);
            req.setAttribute("sortPrice", sortPrice);
            req.setAttribute("searchByTitle", searchByTitle);
            req.setAttribute("product", allProducts);
            req.setAttribute("numberOfPages", numberOfPages);
            req.setAttribute("currentPage", page);

            req.getRequestDispatcher("/screens/ProductLists.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

}
