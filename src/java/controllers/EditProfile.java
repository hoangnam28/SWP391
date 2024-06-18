/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import daos.imples.UserDAO;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.User;

@WebServlet("/editProfile")
@MultipartConfig
public class EditProfile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Assuming user ID is stored in the session
        int Id = (int) request.getSession().getAttribute("Id");

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(Id);
            request.setAttribute("user", user);
            request.getRequestDispatcher("./home_page").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred.");
            request.getRequestDispatcher("./home_page").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user details from the request
        HttpSession session = request.getSession();
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        Part filePart = request.getPart("avatar");
        String avatarFileName = null;
        if (!mobile.matches("^0\\d{9}$")) {
            request.setAttribute("message", "Invalid mobile number. It must be 10 digits long and start with 0.");
            request.getRequestDispatcher("./home_page").forward(request, response);
            return;
        }
        if (filePart != null && filePart.getSize() > 0) {
            avatarFileName = filePart.getSubmittedFileName();
            String uploadDir = getServletContext().getRealPath("/") + "uploads";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdir();
            }
            filePart.write(uploadDir + File.separator + avatarFileName);
        }

        // Assuming user ID is stored in the session
//        int userId = (int) request.getSession().getAttribute("userId");
//        int userId = Integer.parseInt(String(userId));
        String id = request.getParameter("id");

        if (id != null) {

            int userId = Integer.parseInt(id);
            User user = new User();
            user.setId(userId);
            user.setFullName(fullName);
            user.setGender(gender);
            user.setMobile(mobile);
            user.setAddress(address);
            user.setAvatar(avatarFileName);
            if (avatarFileName != null) {
                user.setAvatar(avatarFileName);
            } else {
                // Fetch the current avatar from the session user
                User sessionUser = (User) session.getAttribute("user");
                if (sessionUser != null) {
                    user.setAvatar(sessionUser.getAvatar());
                }
            }
            try {
                UserDAO userDAO = new UserDAO();
                boolean result = userDAO.updateUser(user);
                if (result) {
                    session.setAttribute("user", user);
                    request.setAttribute("message", "Profile updated successfully.");
                } else {
                    request.setAttribute("message", "Profile update failed.");
                }
                request.getRequestDispatcher("./home_page").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "An error occurred.");
                request.getRequestDispatcher("./home_page").forward(request, response);
            }
        }
    }

}
