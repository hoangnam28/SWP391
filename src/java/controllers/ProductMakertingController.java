package controllers;

import daos.imples.CategoryDao;
import daos.imples.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import models.Products;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import models.Category;

@WebServlet(name = "ProductMakertingController", urlPatterns = {"/prouduct_list", "/addProduct", "/editProduct", "/deleteProduct"})
@MultipartConfig
public class ProductMakertingController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CategoryDao categoryDao;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String role = (String) request.getSession().getAttribute("role");

    if (!"Marketing".equals(role)) {
        response.sendRedirect(request.getContextPath() + "/common/unauthorized.jsp");
        return;
    }

    String category = request.getParameter("category");
    String search = request.getParameter("search");
    String sortBy = request.getParameter("sortBy");
    
    // Get page number and size
    int page = 1;
    int pageSize = 5;
    if (request.getParameter("page") != null) {
        page = Integer.parseInt(request.getParameter("page"));
    }

    List<Products> products = null;
    int totalProducts = 0;
    try {
        if (categoryDao == null) {
            categoryDao = new CategoryDao();
        }
        List<Category> categories = categoryDao.findAll();
        request.setAttribute("categories", categories);

        ProductDao productDao = new ProductDao();
        products = productDao.findProducts(category, search, sortBy, page, pageSize);
        totalProducts = productDao.countProducts(category, search);  // Add a method to count the total number of products
    } catch (SQLException e) {
        e.printStackTrace();
    }

    int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

    request.setAttribute("products", products);
    request.setAttribute("category", category);
    request.setAttribute("page", page);
    request.setAttribute("totalPages", totalPages);  // Pass total pages to the JSP
    request.getRequestDispatcher("/screens/MarketingProductList.jsp").forward(request, response);
}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/addProduct")) {
            handleAddProduct(request, response);
        } else if (action.equals("/editProduct")) {
            handleEditProduct(request, response);
        } else if (action.equals("/deleteProduct")) {
            handleDeleteProduct(request, response);
        }
    }

    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Part thumbnailPart = request.getPart("thumbnail");
        String thumbnailFileName = Paths.get(thumbnailPart.getSubmittedFileName()).getFileName().toString();

        String thumbnailPath = null;
        if (!thumbnailFileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            thumbnailPath = uploadPath + File.separator + thumbnailFileName;
            thumbnailPart.write(thumbnailPath);
        }

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
        double salePrice = Double.parseDouble(request.getParameter("salePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Products product = new Products();
        product.setTitle(title);
        product.setDescription(description);
        product.setCategoryId(categoryId);
        product.setOriginalPrice(originalPrice);
        product.setSalePrice(salePrice);
        product.setStock(stock);

        if (thumbnailPath != null) {
            product.setThumbnail(thumbnailFileName);
        }

        try {
            ProductDao productDao = new ProductDao();
            productDao.insert(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("prouduct_list");
    }

    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Part thumbnailPart = request.getPart("thumbnail");
        String thumbnailFileName = Paths.get(thumbnailPart.getSubmittedFileName()).getFileName().toString();

        String thumbnailPath = null;
        if (!thumbnailFileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            thumbnailPath = uploadPath + File.separator + thumbnailFileName;
            thumbnailPart.write(thumbnailPath);
        } else {
            thumbnailFileName = null;
        }

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
        double salePrice = Double.parseDouble(request.getParameter("salePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Products product = new Products();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setCategoryId(categoryId);
        product.setOriginalPrice(originalPrice);
        product.setSalePrice(salePrice);
        product.setStock(stock);

        if (thumbnailFileName != null) {
            product.setThumbnail(thumbnailFileName);
        } else {
            // Get current thumbnail from database
            try {
                ProductDao productDao = new ProductDao();
                Products existingProduct = productDao.findById(id);
                product.setThumbnail(existingProduct.getThumbnail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            ProductDao productDao = new ProductDao();
            productDao.update(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("prouduct_list");
    }

    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try {
            ProductDao productDao = new ProductDao();
            boolean isDeleted = productDao.deleteById1(id);

            if (isDeleted) {
                request.setAttribute("message", "Product deleted successfully.");
            } else {
                request.setAttribute("errorMessage", "Failed to delete the product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to delete the product due to an error.");
        }

        response.sendRedirect("prouduct_list");
    }
}
