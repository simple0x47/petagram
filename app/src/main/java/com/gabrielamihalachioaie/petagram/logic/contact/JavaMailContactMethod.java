package com.gabrielamihalachioaie.petagram.logic.contact;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailContactMethod implements ContactMethod {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_USER = "petagram.official@gmail.com";
    private static final String SMTP_PASSWORD = "21g#L@r10";
    private static final int SMTP_PORT = 465;

    private final Session mailSession;

    public JavaMailContactMethod() {
        Properties smtpProperties = new Properties();
        smtpProperties.put("mail.smtp.host", SMTP_HOST);
        smtpProperties.put("mail.smtp.port", SMTP_PORT);
        smtpProperties.put("mail.smtp.auth", true);
        smtpProperties.put("mail.smtp.ssl.enable", true);

        mailSession = Session.getInstance(smtpProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });
    }

    @Override
    public void sendMessage(String from, String to, String subject, String message) {
        Runnable sendMessageRunnable = () -> {
            try {
                MimeMessage msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                msg.setSubject(subject);
                msg.setText(message);
                msg.setSentDate(new Date());

                Transport.send(msg);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        };

        Thread sendMessageThread = new Thread(sendMessageRunnable);
        sendMessageThread.start();
    }
}