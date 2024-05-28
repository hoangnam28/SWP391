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

        try {
            // Fetch all categories
            List<Category> categories = categoryDao.findAll();
            req.setAttribute("categories", categories);

            // Fetch products based on category
            List<Products> allProducts;
            if (category != null && !category.isEmpty()) {
                allProducts = daoProductController.findByCategory(Integer.parseInt(category));
            } else {
                allProducts = daoProductController.findAll();
            }

            // Search products by description
            if (searchByDescription != null && !searchByDescription.isEmpty()) {
                allProducts = daoProductController.findByDescription(searchByDescription);
            }

            // Sort products by price descending
            if ("price".equals(sortPrice)) {
                allProducts.sort(Comparator.comparingDouble(Products::getSalePrice).reversed());
            }

            req.setAttribute("products", allProducts);

            List<Products> smartphone = daoProductController.findByTitle("smartphone");
            List<Products> laptop = daoProductController.findByTitle("laptop");
            List<Products> headphone = daoProductController.findByTitle("headphone");
            List<Products> watch = daoProductController.findByTitle("watch");

            req.setAttribute("watch", watch);
            req.setAttribute("headphone", headphone);
            req.setAttribute("laptop", laptop);
            req.setAttribute("smartphone", smartphone);

            req.getRequestDispatcher("/screens/ProductLists.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}
