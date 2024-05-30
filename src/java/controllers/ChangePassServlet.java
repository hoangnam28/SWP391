///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//package controllers;
//
//import daos.imples.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.sql.SQLException;
//
///**
// *
// * @author 84397
// */
//public class ChangePassServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//    private UserDAO dao;
//    private CategoryDao categoryDao;
//
//    public void init() throws ServletException {
//        try {
//            dao = new UserDAO();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new ServletException("Cannot initialize DAOs", e);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        int userId = 1; // Change this to get user ID from session
//        //merge code login ve
//        String oldPassword = request.getParameter("oldPassword");
//        String newPassword = request.getParameter("newPassword");
//        String confirmPassword = request.getParameter("confirmPassword");
//        boolean check = dao.currentPass(1, oldPassword);
//        if (check) {
//            if (confirmPassword.equals(newPassword)) {
//                boolean success = dao.changePass(1, newPassword);
//                if (success) {
//                    response.sendRedirect(request.getContextPath() + "/home_page");
//                } else {
//                    response.sendRedirect(request.getContextPath() + "/home_page");
//                }
//            } else {
//                response.sendRedirect(request.getContextPath() + "/home_page");
//            }
//        } else {
//           response.sendRedirect(request.getContextPath() + "/home_page");
//        }
//    }
//}
//
//
//
