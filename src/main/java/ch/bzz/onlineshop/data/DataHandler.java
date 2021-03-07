package ch.bzz.onlineshop.data;

import ch.bzz.onlineshop.model.Artikel;
import ch.bzz.onlineshop.model.Onlineshop;
import ch.bzz.onlineshop.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * data handler for reading and writing the csv files
 * <p>
 * M133: Onlineshop
 *
 * @author Marko Micanovic
 */
public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static List<Onlineshop> onlineshopList = null;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {

    }

    /**
     * gets a list of all onlineshops with their articles
     *
     * @return
     */
    public static List<Onlineshop> getOnlineshopList() {
        if (onlineshopList == null) {
            onlineshopList = new ArrayList<>();
            readJSON();
        }
        return onlineshopList;
    }

    public static List<Artikel> getArtikelList() {
        List<Artikel> artikelList = new ArrayList<>();

        for (Onlineshop onlineshop : getOnlineshopList()) {
            for (Artikel artikel : onlineshop.getAlleArtikel()) {
                artikelList.add(artikel);
            }
        }
        return artikelList;
    }

    /**
     * find the onlineshop for a article
     *
     * @param artikelNummer
     * @return
     */
    public static Onlineshop findOnlineshopByArtikelnummer(String artikelNummer) {
        for (Onlineshop onlineshop : getOnlineshopList()) {
            for (Artikel artikel : onlineshop.getAlleArtikel()) {
                if (artikel.getArtikelNummer().equals(artikelNummer))
                    return onlineshop;
            }
        }
        return null;
    }

    /**
     * gets a article by its artikelNummer
     *
     * @param artikelNummer the uuid of the book
     * @return book-object
     */
    public static Artikel findArtikelByArtikelnummer(String artikelNummer) {
        List<Artikel> artikelList = getArtikelList();
        for (Artikel artikel : artikelList) {
            if (artikel != null && artikel.getArtikelNummer().equals(artikelNummer))
                return artikel;
        }

        return null;
    }

    /**
     * reads the json-file into the publisherList
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("publisherJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Onlineshop[] onlineshops = objectMapper.readValue(jsonData, Onlineshop[].class);
            for (Onlineshop onlineshop : onlineshops) {
                getOnlineshopList().add(onlineshop);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
