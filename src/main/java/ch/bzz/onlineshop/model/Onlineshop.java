package ch.bzz.onlineshop.model;

import java.util.List;

/**
 * a onlineshop of a article
 * <p>
 * Onlineshop
 *
 * @author Marko Micanovic
 */
public class Onlineshop {
    private String onlineshopUUID;
    private String onlineshop;
    private List<Artikel> alleArtikel;

    public String getOnlineshop() {
        return onlineshop;
    }

    public void setOnlineshop(String onlineshop) {
        this.onlineshop = onlineshop;
    }

    /**
     * Gets the artikelMap
     * @return map of articles
     */
    public List<Artikel> getArtikelList() {
        return alleArtikel;
    }

    /**
     * Sets the artikelMap
     * @param alleArtikel
     */
    public void setArtikelList(List<Artikel> alleArtikel) {
        this.alleArtikel = alleArtikel;
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
    public String getOnlineshopUUID() {
        return onlineshopUUID;
    }

    /**
     * sets the onlineshop-url
     * @param onlineshopUUID
     */
    public void setOnlineshopUUID(String onlineshopUUID) {
        this.onlineshopUUID = onlineshopUUID;
    }
}
