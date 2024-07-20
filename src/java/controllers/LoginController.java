package controllers;

import daos.imples.AccountDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            if (email == null || password == null) {
                jsonResponse.put("success", false);
                jsonResponse.put("error", "Email or password is missing");
            } else {
                AccountDao accountDao = new AccountDao();
                User user = accountDao.login(email, password);

                if (user == null) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("error", "Invalid email or password");
                } else if (!user.getStatus().equals("Active")) {
                    jsonResponse.put("success", false);
                    jsonResponse.put("error", "Account inactive!");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("user_id", user.getId());
                    session.setAttribute("role", user.getRole());

                    if ("Marketing".equals(user.getRole())) {
                        jsonResponse.put("redirectUrl", "./customers_list");
                    } else if ("Admin".equals(user.getRole())) {
                        jsonResponse.put("redirectUrl", "./admin_list");
                    } else if ("Sale".equals(user.getRole())) {
                        jsonResponse.put("redirectUrl", "./orders_list");
                    } else {
                        jsonResponse.put("redirectUrl", "./home_page");
                    }
                    jsonResponse.put("success", true);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            jsonResponse.put("success", false);
            jsonResponse.put("error", "An error occurred during login process");
        } finally {
            out.print(jsonResponse.toString());
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
