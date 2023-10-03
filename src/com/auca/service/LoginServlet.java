package com.auca.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String enteredPassword = request.getParameter("password");

 
        if (isValidUser(username, enteredPassword)) {

            HttpSession session = request.getSession(true);
            session.setAttribute("authenticatedUser", username);


            sendConfirmationEmail(request.getParameter("email"));

            response.sendRedirect("welcome.html");
        } else {

            response.sendRedirect("login_error.html");
        }
    }

    private boolean isValidUser(String username, String password) {

        return "your_username".equals(username) && "your_password".equals(password);
    }

    private void sendConfirmationEmail(String candidateEmail) {
        
        final String username = "nshukenny@gmail.com";
        final String password = "123-Kigali";

        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587"); 

        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            e
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(candidateEmail));
            message.setSubject("Submission Confirmation");

            String confirmationMessage = "Dear Candidate,\n\n" + "Your submission has been received and confirmed.\n"
                    + "Thank you for applying!\n\n" + "Best regards,\nAUCA";

            message.setText(confirmationMessage);

           
            Transport.send(message);

            System.out.println("Confirmation email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending confirmation email: " + e.getMessage());
        }
    }
}
