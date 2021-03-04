package ch.bzz.onlineshop.model;

import java.util.ArrayList;

public class Onlineshop {
    private ArrayList<Artikel> alleArtikel;
    private String url;
    private int anzahlRegistrierteBenutzer;
    private int anzahlBestellungen;

    public ArrayList<Artikel> getAlleArtikel() {
        return alleArtikel;
    }

    public void setAlleArtikel(ArrayList<Artikel> alleArtikel) {
        this.alleArtikel = alleArtikel;
    }

    public Artikel getArtikel(int index){
        return alleArtikel.get(index);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAnzahlRegistrierteBenutzer() {
        return anzahlRegistrierteBenutzer;
    }

    public void setAnzahlRegistrierteBenutzer(int anzahlRegistrierteBenutzer) {
        this.anzahlRegistrierteBenutzer = anzahlRegistrierteBenutzer;
    }

    public int getAnzahlBestellungen() {
        return anzahlBestellungen;
    }

    public void setAnzahlBestellungen(int anzahlBestellungen) {
        this.anzahlBestellungen = anzahlBestellungen;
    }
}
