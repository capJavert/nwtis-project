package org.foi.nwtis.antbaric.components;

public class Izbornik
{
    private String labela;
    private String vrijednost;

    public Izbornik(String labela, String vrijednost)
    {
        this.labela = labela;
        this.vrijednost = vrijednost;
    }
    
    public String getLabela()
    {
        return labela;
    }

    public String getVrijednost()
    {
        return vrijednost;
    }

    public void setLabela(String labela)
    {
        this.labela = labela;
    }

    public void setVrijednost(String vrijednost)
    {
        this.vrijednost = vrijednost;
    }
}
