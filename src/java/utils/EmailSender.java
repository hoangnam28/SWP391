/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.InputStream;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;

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

    public static void sendEmailToConfirmOrder(String recipientEmail) throws MessagingException {
        String host = "smtp.gmail.com"; // Máy chủ SMTP của Gmail
        String user = "ngominhtoan2812@gmail.com";
        String pass = "titjgnwuyaepycet";
        String from = "ngominhtoan2812@gmail.com";
        String subject = "Email Verification";

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
        msg.setText("Hello");

        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>Pay for your order online by scanning the QR code provided below: </H1>";
        htmlText += "<img src=\"http://drive.google.com/uc?id=19PtgPN0scwIH2D_ZqHHiKnJJanQn3LYn\">";
        String htmlText2 = "<H2>TechStore thanks for your order! </H2>";
        messageBodyPart.setContent(htmlText, "text/html");
        multipart.addBodyPart(messageBodyPart);
        try {
            messageBodyPart = new MimeBodyPart();
            msg.setContent(multipart);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Done");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
