/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.mail;

import challenge.config.Config;
import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Sérgio
 */
public class Teste {
    public static void sendEmail(String dest, String subject, String text) {
        Email mail = new Email();
        mail.setDestination(dest).setSubject(subject).setText(text);
        try {
            mail.send();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        final String username = args[0];
        final String password = args[1];
        final String host = args[2];
        
        MailSSLSocketFactory socketFactory;
        try {
            socketFactory = new MailSSLSocketFactory();
            //socketFactory.setTrustAllHosts(true);
            socketFactory.setTrustedHosts(new String[] {host});
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.socketFactory", socketFactory);
            Session session = Session.getInstance(props, new Authenticator() {

                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("srsivelli@hotmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("sergiorsivelli@gmail.com"));
                message.setSubject("A testing mail header !!!");
                message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, e);
            }        
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
