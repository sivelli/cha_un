/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.mail;

import challenge.config.Config;
import com.sun.mail.util.MailSSLSocketFactory;
import java.security.GeneralSecurityException;
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
public class Email {
    private final String from;
    private final String username;
    private final String password;
    private final String smtpServer;
    private final String smtpPort;
    private String destination;
    private String subject;
    private String text;

    public Email() {
        this.from = Config.getInstance().getVarToString("email_from_address");
        this.username = Config.getInstance().getVarToString("email_username");
        this.password = Config.getInstance().getVarToString("email_password");
        this.smtpServer = Config.getInstance().getVarToString("email_smtp_host");
        this.smtpPort = Config.getInstance().getVarToString("email_smtp_port");
    }

    public Email setDestination(String email) {
        this.destination = email;
        return this;
    }

    public Email setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Email setText(String text) {
        this.text = text;
        return this;
    }

    public boolean send() throws MessagingException {
        if (this.destination == null) {
            return false;
        }
        else {
            MailSSLSocketFactory socketFactory;
            try {
                socketFactory = new MailSSLSocketFactory();
                //socketFactory.setTrustAllHosts(true);
                socketFactory.setTrustedHosts(new String[] {smtpServer});
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", smtpServer);
                props.put("mail.smtp.port", (smtpPort == null ? "587" : smtpPort));
                props.put("mail.smtp.ssl.socketFactory", socketFactory);
                Session session = Session.getInstance(props, new Authenticator() {

                    @Override
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
                try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
                    message.setSubject(subject);
                    message.setText(text);

                    Transport.send(message);

                    return true;

                } catch (MessagingException e) {
                    Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
                    throw e;
                }
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }
}
