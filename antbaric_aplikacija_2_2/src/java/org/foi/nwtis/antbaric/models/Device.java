package org.foi.nwtis.antbaric.models;

import java.sql.Timestamp;

/**
 *
 * @author javert
 */
public class Device {

    private Integer id;
    private String naziv;
    private Float latitude;
    private Float longitude;
    private Integer status;
    private Timestamp vrijeme_promjene;
    private Timestamp vrijeme_kreiranja;

    public Integer getPrimaryKey() {
        return this.id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getVrijeme_promjene() {
        return vrijeme_promjene;
    }

    public void setVrijeme_promjene(Timestamp vrijeme_promjene) {
        this.vrijeme_promjene = vrijeme_promjene;
    }

    public Timestamp getVrijeme_kreiranja() {
        return vrijeme_kreiranja;
    }

    public void setVrijeme_kreiranja(Timestamp vrijeme_kreiranja) {
        this.vrijeme_kreiranja = vrijeme_kreiranja;
    }
    
}
