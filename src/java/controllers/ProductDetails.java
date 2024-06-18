/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import daos.imples.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Products;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/productDetails")
public class ProductDetails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            try {
                int productId = Integer.parseInt(id);
                ProductDao productDao = new ProductDao();
                Products product = productDao.findById(productId);
                List<String> categories = productDao.findAllCategories();
                List<Products> latestProducts = productDao.findLatestProducts();

                request.setAttribute("product", product);
                request.setAttribute("categories", categories);
                request.setAttribute("latestProducts", latestProducts);
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("/screens/productDetails.jsp").forward(request, response);
    }
}
