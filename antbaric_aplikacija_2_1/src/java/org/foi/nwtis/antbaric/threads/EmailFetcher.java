package org.foi.nwtis.antbaric.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import org.foi.nwtis.antbaric.beans.EmailCheck;
import org.foi.nwtis.antbaric.components.EmailMessage;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;

/**
 *
 * @author javert
 *
 */
public class EmailFetcher extends Thread {

    private Boolean run = true;
    private final ArrayList<EmailMessage> emailMessages = new ArrayList<>();
    private String posluzitelj;
    private String korisnik;
    private String lozinka;
    private Konfiguracija config = null;
    private String filter;

    @Override
    public void interrupt() {
        this.run = false;
        super.interrupt();
    }

    @Override
    public void run() {
        this.config = EmailCheck.getConfig();
        Integer interval = Integer.parseInt(config.dajPostavku("mail.timeSecThread"));
        this.korisnik = this.config.dajPostavku("mail.usernameThread");
        this.lozinka = this.config.dajPostavku("mail.passwordThread");
        this.posluzitelj = this.config.dajPostavku("mail.server");
        this.filter = this.config.dajPostavku("mail.subject");

        System.out.println("Init thread...");
        while (true) {
            try {
                this.processMessages();
            } catch (IOException ex) {
                System.out.println("Error while working with messages...");
            }

            try {
                System.out.println("...");
                Thread.sleep(interval * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EmailFetcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    void processMessages() throws IOException {
        this.emailMessages.clear();

        // Start the session
        java.util.Properties properties = System.getProperties();
        properties.put("mail.smtp.host", this.posluzitelj);
        Session session = Session.getInstance(properties, null);

        // Connect to the store
        try {
            Store store = session.getStore("imap");
            store.connect(this.posluzitelj, this.korisnik, this.lozinka);

            // Open the INBOX folder
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            Message[] messages = folder.getMessages();

            for (int i = 0; i < messages.length; ++i) {
                if (messages[i].getSubject().contains(this.filter)) {
                    messages[i].setFlag(Flags.Flag.SEEN, true);
                    this.moveToFolder(folder, this.config.dajPostavku("mail.folderNWTiS"), messages[i], store);
                }
            }

            Folder folder2 = store.getFolder(this.config.dajPostavku("mail.folderNWTiS"));
            if (!folder2.exists()) {
                folder2.create(Folder.HOLDS_MESSAGES);
            }
            folder2.open(Folder.READ_ONLY);
            Message[] folderMessages = folder2.getMessages();

            System.out.println("NWTIS messages: ");
            for (int i = 0; i < folderMessages.length; ++i) {
                System.out.println(folderMessages[i].getContent().toString());
            }

            folder.close(true);
            store.close();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(EmailFetcher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void moveToFolder(Folder fromFolder, String toFolder, Message message, Store store) {
        Message[] messages = new Message[]{message};
        try {
            Folder dfolder = store.getFolder(toFolder);
            if (!dfolder.exists()) {
                dfolder.create(Folder.HOLDS_MESSAGES);
            }
            fromFolder.copyMessages(messages, dfolder);
            fromFolder.setFlags(messages, new Flags(Flags.Flag.DELETED), true);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
