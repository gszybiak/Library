package com.example.firstapp.Email;

import com.example.firstapp.controller.Book;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.LocalDate;
import java.util.Properties;
import javax.activation.FileDataSource;

public class SendEmail {

    public static void sendInfoRend(String receiver, LocalDate localDate, Book book) {
        String from = "glibrary@op.pl";
        String password = "Glibrary.123";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.poczta.onet.pl");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new MyAuthenticator(from, password));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Powiadomienie o wypożyczeniu książki ");
            message.setText("Dzień dobry\nPowiadamy o wypożyczeniu książki " + book.title + ".\n" +
                    "Książka powinna być oddana do dnia " + localDate.toString() + ".\nDo zobaczenia!");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendInfoReturn(String receiver, Book book) {
        String from = "glibrary@op.pl";
        String password = "Glibrary.123";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.op.pl");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new MyAuthenticator(from, password));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Powiadomienie o zwróceniu książki ");
            message.setText("Dzień dobry\nPowiadamy o zwrocie książki " + book.title + ".\n" +
                    "Dziękujemy za terminowy zwrot.\nDo zobaczenia!");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendAllInformation(String receiver) {
        String from = "glibrary@op.pl";
        String password = "Glibrary.123";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.op.pl");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new MyAuthenticator(from, password));

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Powiadomienie o zwróceniu książki ");
            MimeBodyPart textBody = new MimeBodyPart();
            textBody.setText("Witaj\nW załączniku przesyłam dane wszystkich książek!\nPozdrawiam");
            MimeBodyPart attachmentBody = new MimeBodyPart();
            String filename = "file.txt";
            DataSource source = new FileDataSource(filename);
            attachmentBody.setDataHandler(new DataHandler(source));
            attachmentBody.setFileName(filename);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBody);
            multipart.addBodyPart(attachmentBody);
            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
