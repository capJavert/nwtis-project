package org.foi.nwtis.antbaric.ws;

import org.foi.nwtis.dkermek.ws.serveri.StatusKorisnika;
import org.foi.nwtis.dkermek.ws.serveri.StatusUredjaja;

/**
 *
 * @author javert
 */
public class IoTMasterWSClient {
    private final String username;
    private final String password;
    
    public IoTMasterWSClient(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Boolean aktivirajGrupuIoT() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.aktivirajGrupuIoT(this.username, this.password);
    }

    public boolean aktivirajOdabraneUredjajeGrupe(java.util.List<java.lang.Integer> odabraniUredjaji) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.aktivirajOdabraneUredjajeGrupe(this.username, this.password, odabraniUredjaji);
    }

    public boolean aktivirajUredjajGrupe(int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.aktivirajUredjajGrupe(this.username, this.password, idUredjaj);
    }

    public Boolean autenticirajGrupuIoT() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.autenticirajGrupuIoT(this.username, this.password);
    }

    public Boolean blokirajGrupuIoT() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.blokirajGrupuIoT(this.username, this.password);
    }

    public boolean blokirajOdabraneUredjajeGrupe(java.util.List<java.lang.Integer> odabraniUredjaji) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.blokirajOdabraneUredjajeGrupe(this.username, this.password, odabraniUredjaji);
    }

    public boolean blokirajUredjajGrupe(int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.blokirajUredjajGrupe(this.username, this.password, idUredjaj);
    }

    public StatusKorisnika dajStatusGrupeIoT() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.dajStatusGrupeIoT(this.username, this.password);
    }

    public StatusUredjaja dajStatusUredjajaGrupe(int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.dajStatusUredjajaGrupe(this.username, this.password, idUredjaj);
    }

    public java.util.List<org.foi.nwtis.dkermek.ws.serveri.Uredjaj> dajSveUredjajeGrupe() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.dajSveUredjajeGrupe(this.username, this.password);
    }

    public Boolean deregistrirajGrupuIoT() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.deregistrirajGrupuIoT(this.username, this.password);
    }

    public Boolean dodajNoviUredjajGrupi(int idUredjaj, java.lang.String nazivUredjaj, java.lang.String adresaUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.dodajNoviUredjajGrupi(this.username, this.password, idUredjaj, nazivUredjaj, adresaUredjaj);
    }

    public Boolean dodajUredjajGrupi(org.foi.nwtis.dkermek.ws.serveri.Uredjaj iotUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.dodajUredjajGrupi(this.username, this.password, iotUredjaj);
    }

    public boolean obrisiOdabraneUredjajeGrupe(java.util.List<java.lang.Integer> odabraniUredjaji) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.obrisiOdabraneUredjajeGrupe(this.username, this.password, odabraniUredjaji);
    }

    public Boolean obrisiSveUredjajeGrupe() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.obrisiSveUredjajeGrupe(this.username, this.password);
    }

    public boolean obrisiUredjajGrupe(int idUredjaj) {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.obrisiUredjajGrupe(this.username, this.password, idUredjaj);
    }

    public Boolean registrirajGrupuIoT() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.registrirajGrupuIoT(this.username, this.password);
    }

    public boolean ucitajSveUredjajeGrupe() {
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service service = new org.foi.nwtis.dkermek.ws.serveri.IoTMaster_Service();
        org.foi.nwtis.dkermek.ws.serveri.IoTMaster port = service.getIoTMasterPort();
        return port.ucitajSveUredjajeGrupe(this.username, this.password);
    }
    
}
