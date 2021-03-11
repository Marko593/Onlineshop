package ch.bzz.onlineshop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * a onlineshop of a article
 * <p>
 * Onlineshop
 *
 * @author Marko Micanovic
 */
public class Onlineshop {
    private String url;
    private int anzahlRegistrierteBenutzer;
    private int anzahlBestellungen;
    private List<Artikel> alleArtikel;

    /**
     * Gets the artikelMap
     * @return map of articles
     */
    public List<Artikel> getAlleArtikel() {
        return alleArtikel;
    }

    /**
     * sets the artikelMap
     * @param alleArtikel
     */
    public void setAlleArtikel(List<Artikel> alleArtikel) {
        this.alleArtikel = alleArtikel;
    }

    /**
     * gets a single article by index
     * @param index
     * @return single article
     */
    public Artikel getArtikel(int index){
        return alleArtikel.get(index);
    }

    /**
     * gets the onlineshop-url
     * @return onlineshop-url
     */
    public String getUrl() {
        return url;
    }

    /**
     * sets the onlineshop-url
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * gets the number of registered users
     * @return number of registered users
     */
    public int getAnzahlRegistrierteBenutzer() {
        return anzahlRegistrierteBenutzer;
    }

    /**
     * sets the number of registered users
     * @param anzahlRegistrierteBenutzer
     */
    public void setAnzahlRegistrierteBenutzer(int anzahlRegistrierteBenutzer) {
        this.anzahlRegistrierteBenutzer = anzahlRegistrierteBenutzer;
    }

    /**
     * gets the number of completed orders
     * @return number of completed orders
     */
    public int getAnzahlBestellungen() {
        return anzahlBestellungen;
    }

    /**
     * sets the number of completed orders
     * @param anzahlBestellungen
     */
    public void setAnzahlBestellungen(int anzahlBestellungen) {
        this.anzahlBestellungen = anzahlBestellungen;
    }
}
