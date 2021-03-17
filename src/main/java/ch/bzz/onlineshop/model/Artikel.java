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
    public String getOnlineshopURL(){
        Onlineshop onlineshop = DataHandler.findOnlineshopByArtikelnummer(getArtikelNummer());
        return onlineshop.getOnlineshopUUID();
    }
    /**
     * gets the registered users from onlineshop
     * @return the number of registered users
     */
    @JsonIgnore
    public int getOnlineshopRegistrierteBenutzer(){
        Onlineshop onlineshop = DataHandler.findOnlineshopByArtikelnummer(getArtikelNummer());
        return onlineshop.getAnzahlRegistrierteBenutzer();
    }

    /**
     * gets the number of completed orders from onlineshop
     * @return the number of completed orders
     */
    @JsonIgnore
    public int getOnlineshopAnzahlBestellungen(){
        Onlineshop onlineshop = DataHandler.findOnlineshopByArtikelnummer(getArtikelNummer());
        return onlineshop.getAnzahlBestellungen();
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
