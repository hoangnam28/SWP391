/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import daos.imples.SliderDao;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Slider;
import ulti.Helper;

/**
 *
 * @author admin
 */
public class SliderControllers extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int page = 1;
            String text = request.getParameter("title");
            String status = request.getParameter("status");
            String pagenum = request.getParameter("page");
            if(pagenum != null){
                page = Integer.parseInt(pagenum);
            }
            try {
                SliderDao sliderDao = new SliderDao();
                List<Slider> sliders = sliderDao.findAll();
                if(sliders != null){
                    if(text != null && !text.trim().isEmpty()){
                        request.setAttribute("text", text);
                        sliders = sliders.stream().filter(n -> n.getTitle().toLowerCase().equals(text.toLowerCase())).toList();
                    }
                    if(status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("All")){
                        request.setAttribute("status", status);
                        sliders = sliders.stream().filter(n -> status.equals(n.getStatus())).toList();
                    }
                }
                List list = Helper.pagination(sliders, page, 5);
                int totalPage = sliders != null && sliders.size() % 5 == 0 ? sliders.size() / 5 : (sliders.size() / 5 + 1);
                request.setAttribute("total", totalPage);
                request.setAttribute("list", list);
                request.setAttribute("pagenum", page);
                request.getRequestDispatcher("screens/SliderList.jsp").forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(SliderControllers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
