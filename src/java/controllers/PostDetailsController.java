/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import models.Post;
import daos.imples.PostDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostDetailsController", urlPatterns = {"/post_details"})
public class PostDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("id"));
        Post post = null;
        try {
            PostDao postDao = new PostDao();
            post = postDao.findById(postId);
            List<String> categories = postDao.getAllCategories(); // Assuming you have a method to retrieve all categories from the database
            request.setAttribute("categories", categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("post", post);
        request.getRequestDispatcher("/screens/BlogDetails.jsp").forward(request, response);
    }
}
