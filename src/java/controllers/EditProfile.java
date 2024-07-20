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
import org.json.JSONObject;

@WebServlet("/editProfile")
@MultipartConfig
public class EditProfile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession().getAttribute("user_id");

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findById(userId);
            request.setAttribute("user", user);
            request.getRequestDispatcher("./home_page").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred.");
            request.getRequestDispatcher("./home_page").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession();
        
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        Part filePart = request.getPart("avatar");
        String avatarFileName = null;

        if (!mobile.matches("^0\\d{9}$")) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Invalid mobile number. It must be 10 digits long and start with 0.");
            response.getWriter().print(jsonResponse.toString());
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

        String id = request.getParameter("id");

        if (id != null) {
            int userId = Integer.parseInt(id);
            User user = new User();
            user.setId(userId);
            user.setFullName(fullName);
            user.setGender(gender);
            user.setMobile(mobile);
            user.setAddress(address);
            if (avatarFileName != null) {
                user.setAvatar(avatarFileName);
            } else {
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
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Profile updated successfully.");
                } else {
                    jsonResponse.put("success", false);
                    jsonResponse.put("message", "Profile update failed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                jsonResponse.put("success", false);
                jsonResponse.put("message", "An error occurred.");
            }
        }

        response.getWriter().print(jsonResponse.toString());
    }
}
