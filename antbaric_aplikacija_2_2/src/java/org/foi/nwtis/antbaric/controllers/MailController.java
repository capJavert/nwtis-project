package org.foi.nwtis.antbaric.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.ServletContext;
import org.foi.nwtis.antbaric.beans.UserAuth;
import org.foi.nwtis.antbaric.konfiguracije.Konfiguracija;
import org.foi.nwtis.antbaric.components.Izbornik;

@Named(value = "mailController")
@RequestScoped
public class MailController extends Controller<Message> {

    private UserAuth userAuth;
    int brojPrikazanihPoruka = 0;
    int currentPage;
    int messageFrom;
    int messageTo;

    public int getMessageFrom() {
        return messageFrom;
    }

    public int getMessageTo() {
        return messageTo;
    }
    Boolean lastPage;

    public Boolean getLastPage() {
        return lastPage;
    }

    private int ukupnoPorukaMapa = 0;

    private String posluzitelj;
    private String port;
    private String korisnik;
    private String lozinka;
    private String odabranaMapa;
    private Konfiguracija conf = null;
    private ServletContext sc = null;
    private String filter = "";

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    private ArrayList<Izbornik> mape = new ArrayList<>();
    private Message[] poruke;

    @PostConstruct
    public void init() {
        this.userAuth = (UserAuth) request.getSession().getAttribute("user");

        if (this.userAuth == null) {
            this.toIndex();
        }
        
        this.sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        this.conf = (Konfiguracija) sc.getAttribute("main-config");
        this.korisnik = this.conf.dajPostavku("mail.usernameThread");
        this.lozinka = this.conf.dajPostavku("mail.passwordThread");
        this.posluzitelj = this.conf.dajPostavku("mail.server");
        this.port = this.conf.dajPostavku("mail.port");
        
        if(this.request.getSession().getAttribute("mapa") == null) {
            this.odabranaMapa = "INBOX";
        } else {
            this.odabranaMapa = this.request.getSession().getAttribute("mapa").toString();
        }
        

        try {
            this.preuzmiMape();
            this.preuzmiPoruke();
        } catch (IOException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.pagination = Integer.parseInt(this.conf.dajPostavku("ui.perPage"));
    }

    void preuzmiMape() {
        // Start the session
        java.util.Properties properties = System.getProperties();
        properties.put("mail.smtp.host", posluzitelj);
        Session session = Session.getInstance(properties, null);

        // Connect to the store
        try {
            Store store = session.getStore("imap");
            store.connect(posluzitelj, korisnik, lozinka);

            Folder[] f = store.getDefaultFolder().list();
            for (Folder fd : f) {
                mape.add(new Izbornik(fd.getName(), fd.getName()));
            }
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void preuzmiPoruke() throws IOException {
        try {
            this.currentPage = Integer.parseInt(this.sc.getAttribute("page").toString());
        } catch (NullPointerException ex) {
            this.currentPage = 0;
            this.sc.setAttribute("page", this.currentPage);
        }

        String server = this.conf.dajPostavku("mail.server");
        String port = this.conf.dajPostavku("mail.port");
        String status;

        // Start the session
        java.util.Properties properties = System.getProperties();
        properties.put("mail.smtp.host", server);
        Session session = Session.getInstance(properties, null);

        // Connect to the store
        try {
            Store store = session.getStore("imap");
            store.connect(server, korisnik, lozinka);

            // Open the INBOX folder
            Folder folder = store.getFolder(this.odabranaMapa);
            folder.open(Folder.READ_ONLY);

            this.ukupnoPorukaMapa = folder.getMessageCount();

            this.poruke = folder.getMessages();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void truncateFolder() {
        try {
            this.currentPage = Integer.parseInt(this.sc.getAttribute("page").toString());
        } catch (NullPointerException ex) {
            this.currentPage = 0;
            this.sc.setAttribute("page", this.currentPage);
        }

        String server = this.conf.dajPostavku("mail.server");
        String port = this.conf.dajPostavku("mail.port");
        String status;

        // Start the session
        java.util.Properties properties = System.getProperties();
        properties.put("mail.smtp.host", server);
        Session session = Session.getInstance(properties, null);

        // Connect to the store
        try {
            Store store = session.getStore("imap");
            store.connect(server, korisnik, lozinka);

            // Open the INBOX folder
            Folder folder = store.getFolder(this.odabranaMapa);
            folder.open(Folder.READ_WRITE);

            this.ukupnoPorukaMapa = folder.getMessageCount();
            
             folder.setFlags(folder.getMessages(), new Flags(Flags.Flag.DELETED), true);
             
             try {
                folder.close(true); 
                store.close();
             } catch(MessagingException ex) { 
                  Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
             }
             
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void promjenaMape() throws IOException {
        request.getSession().setAttribute("mapa", this.odabranaMapa);
    }

    public int getUkupnoPorukaMapa() {
        return ukupnoPorukaMapa;
    }

    public void setUkupnoPorukaMapa(int ukupnoPorukaMapa) {
        this.ukupnoPorukaMapa = ukupnoPorukaMapa;
    }

    public String getOdabranaMapa() {
        return odabranaMapa;
    }

    public void setOdabranaMapa(String odabranaMapa) {
        this.odabranaMapa = odabranaMapa;
    }

    public ArrayList<Izbornik> getMape() {
        return mape;
    }

    public Message[] getPoruke() {
        return poruke;
    }

    public int getBrojPrikazanihPoruka() {
        return brojPrikazanihPoruka;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
