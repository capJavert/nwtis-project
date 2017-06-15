/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.antbaric.models;

/**
 *
 * @author javert
 */
public class Log {

    private Integer id;
    private String korisnik;
    private String data;
    private String ipadresa;
    private Long trajanje;
    private String vrijeme;

    public String getVrijeme() {
        return vrijeme;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public String getData() {
        return data;
    }

    public String getIpadresa() {
        return ipadresa;
    }

    public Long getTrajanje() {
        return trajanje;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setIpadresa(String ipadresa) {
        this.ipadresa = ipadresa;
    }

    public void setTrajanje(Long trajanje) {
        this.trajanje = trajanje;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public void write(String user, String command, String ip, Long trajanje) {
        this.korisnik = user;
        this.data = command;
        this.ipadresa = ip;
        this.trajanje = trajanje;
    }

    public Integer getPrimaryKey() {
        return this.id;
    }

}
