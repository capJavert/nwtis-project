package org.foi.nwtis.antbaric.models;

/**
 * Meteo model
 *
 * @author javert
 */
public class Meteo {

    private Integer id_meteo;
    private Integer id;
    private String adresa_stanice;
    private Double latitude;
    private Double longitude;
    private String vrijeme;
    private String vrijeme_opis;
    private Double temp;
    private Double temp_min;
    private Double temp_max;
    private Double vlaga;
    private Double tlak;
    private Double vjetar;
    private Double vjetar_smjer;
    private String preuzeto;

    public void setPrimaryKey(Integer id) {
        this.id_meteo = id;
    }

    public Integer getPrimaryKey() {
        return this.id_meteo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdresa_stanice() {
        return adresa_stanice;
    }

    public void setAdresa_stanice(String adresa_stanice) {
        this.adresa_stanice = adresa_stanice;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public String getVrijeme_opis() {
        return vrijeme_opis;
    }

    public void setVrijeme_opis(String vrijeme_opis) {
        this.vrijeme_opis = vrijeme_opis;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public Double getVlaga() {
        return vlaga;
    }

    public void setVlaga(Double vlaga) {
        this.vlaga = vlaga;
    }

    public Double getTlak() {
        return tlak;
    }

    public void setTlak(Double tlak) {
        this.tlak = tlak;
    }

    public Double getVjetar() {
        return vjetar;
    }

    public void setVjetar(Double vjetar) {
        this.vjetar = vjetar;
    }

    public Double getVjetar_smjer() {
        return vjetar_smjer;
    }

    public void setVjetar_smjer(Double vjetar_smjer) {
        this.vjetar_smjer = vjetar_smjer;
    }

    public String getPreuzeto() {
        return preuzeto;
    }

    public void setPreuzeto(String preuzeto) {
        this.preuzeto = preuzeto;
    }

}
