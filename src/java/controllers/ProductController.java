package controllers;

import daos.imples.ProductDao;
import models.Products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/products")
public class ProductController extends HttpServlet {

    private ProductDao daoProductController;

    @Override
    public void init() throws ServletException {
        try {
            daoProductController = new ProductDao();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Cannot initialize DAOProductController", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchByDescription = req.getParameter("search");
        String category = req.getParameter("category");
        String sortPrice = req.getParameter("sort");
        
       

        try {
               // Lấy danh sách sản phẩm theo category
            List<Products> allProducts;
            if (category != null && !category.isEmpty()) {
                allProducts = daoProductController.findByCategory(category);
            } else {
                allProducts = daoProductController.findAll();
            }

               // Tìm kiếm sản phẩm theo mô tả
            if (searchByDescription != null && !searchByDescription.isEmpty()) {
                allProducts = allProducts.stream()
                        .filter(product -> product.getDescription().toLowerCase().contains(searchByDescription.toLowerCase()))
                        .collect(Collectors.toList());
            }

            // Sắp xếp sản phẩm theo giá giảm dần
            if ("price".equals(sortPrice)) {
                allProducts.sort(Comparator.comparingDouble(Products::getSalePrice).reversed());
            }
            
            List<Products> smartphone = daoProductController.findByTitle("smartphone");
            List<Products> laptop = daoProductController.findByTitle("laptop");
            List<Products> headphone = daoProductController.findByTitle("headphone");
            List<Products> watch = daoProductController.findByTitle("watch");

            req.setAttribute("watch", watch);
            req.setAttribute("headphone", headphone);
            req.setAttribute("laptop", laptop);
            req.setAttribute("smartphone", smartphone);
            req.setAttribute("categories", daoProductController.findAllCategories());
            req.getRequestDispatcher("/screens/ProductLists.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
}
