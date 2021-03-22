package ch.bzz.onlineshop.model;

import ch.bzz.onlineshop.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * a article in the onlineshop
 * <p>
 * Onlineshop
 *
 * @author Marko Micanovic
 */
public class Artikel {
    private String name;
    private double preis;
    private int stueckzahl;
    private String artikelNummer; //RegEx

    public Artikel(){
        setName(null);
        setPreis(0);
        setStueckzahl(0);
        setArtikelNummer(null);
    }

    /**
     * gets the onlineshop-url of the onlineshop from onlineshopList
     * @return the onlineshop-url
     */
    @JsonIgnore
    public String getOnlineshopUUID(){
        Onlineshop onlineshop = DataHandler.findOnlineshopByArtikelnummer(getArtikelNummer());
        return onlineshop.getOnlineshopUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public int getStueckzahl() {
        return stueckzahl;
    }

    public void setStueckzahl(int stueckzahl) {
        this.stueckzahl = stueckzahl;
    }

    public String getArtikelNummer() {
        return artikelNummer;
    }

    public void setArtikelNummer(String artikelNummer) {
        this.artikelNummer = artikelNummer;
    }
}
