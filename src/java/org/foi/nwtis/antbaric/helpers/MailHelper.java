package org.foi.nwtis.antbaric.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.web.listeners.ApplicationListener;

/**
 *
 * @author javert
 */
public class MailHelper {

    public static Boolean send(String content) {
        try {
            Konfiguracija config = (Konfiguracija) ApplicationListener.getContext().getAttribute("main-config");
            String server = config.dajPostavku("mail.server");
            
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", server);
            
            Session session = Session.getInstance(properties, null);
            MimeMessage message = new MimeMessage(session);
            
            Address fromAddress = new InternetAddress(config.dajPostavku("mail.from"));
            message.setFrom(fromAddress);
            
            Address[] toAddresses = InternetAddress.parse(config.dajPostavku("mail.to"));
            message.setRecipients(Message.RecipientType.TO, toAddresses);
            
            message.setSubject(config.dajPostavku("mail.subject"));
            message.setHeader("Content-Type", "text/plain");
            message.setHeader("charset", "UTF-8");
            message.setText(content);
            
            Transport.send(message);
            
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(MailHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
