/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {

    public static void sendVerificationEmail(String recipientEmail, String verificationLink) throws MessagingException {
        String host = "smtp.gmail.com"; // Máy chủ SMTP của Gmail
        String user = "ngominhtoan2812@gmail.com";
        String pass = "titjgnwuyaepycet";
        String from = "ngominhtoan2812@gmail.com";
        String subject = "Email Verification";
        String messageText = "Click the link to verify your email: " + verificationLink;

        boolean sessionDebug = false;

        // Thiết lập các thuộc tính cho phiên gửi email
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");

        // Thiết lập phiên làm việc
        Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });
        mailSession.setDebug(sessionDebug);

        // Tạo đối tượng MimeMessage
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = {new InternetAddress(recipientEmail)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        msg.setSentDate(new java.util.Date());
        msg.setText(messageText);

        // Gửi email
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(host, user, pass);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }
}

