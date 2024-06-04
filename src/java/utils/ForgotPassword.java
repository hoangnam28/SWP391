package utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet("/forgot-password")
public class ForgotPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/SWP_391";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "nam281002";

    // Method to check if email exists in the database
    private boolean emailExists(String email) {
        boolean exists = false;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SWP_391", "root",
						"nam281002")) {
            String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        exists = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        RequestDispatcher dispatcher = null;
        int otpvalue = 0;
        HttpSession mySession = request.getSession();

        if (email != null && !email.equals("")) {
            if (emailExists(email)) {
                // sending otp
                Random rand = new Random();
                otpvalue = rand.nextInt(1255650);

                String to = email;// change accordingly
                // Get the session object
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");
                Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ngominhtoan2812@gmail.com", "titjgnwuyaepycet");// Put your email id and password here
                    }
                });
                // compose message
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("ngominhtoan2812@gmail.com"));// change accordingly
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    message.setSubject("Hello");
                    message.setText("your OTP is: " + otpvalue);
                    // send message
                    Transport.send(message);
                    System.out.println("message sent successfully");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                dispatcher = request.getRequestDispatcher("./screens/EnterOtp.jsp");
                request.setAttribute("message", "OTP is sent to your email id");
                mySession.setAttribute("otp", otpvalue);
                mySession.setAttribute("email", email);
                dispatcher.forward(request, response);
            } else {
                dispatcher = request.getRequestDispatcher("./screens/forgotPassword.jsp");
                request.setAttribute("message", "Email does not exist in our database");
                dispatcher.forward(request, response);
            }
        } else {
            dispatcher = request.getRequestDispatcher("./screens/forgotPassword.jsp");
            request.setAttribute("message", "Please enter a valid email address");
            dispatcher.forward(request, response);
        }
    }
}
