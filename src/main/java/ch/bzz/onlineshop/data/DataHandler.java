package ch.bzz.onlineshop.data;

import ch.bzz.onlineshop.model.Artikel;
import ch.bzz.onlineshop.model.Onlineshop;
import ch.bzz.onlineshop.model.User;
import ch.bzz.onlineshop.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * data handler for reading and writing the csv files
 * <p>
 * M133: Onlineshop
 *
 * @author Marko Micanovic
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Artikel> artikelMap;
    private static Map<String, Onlineshop> onlineshopMap;
    private static Map<String, User> userMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        artikelMap = new HashMap<>();
        onlineshopMap = new HashMap<>();
        userMap = new HashMap<>();
        readJSON();
    }

    /**
     * reads a single Article identified by its uuid
     * @param artikelNummer  the identifier
     * @return article-object
     */
    public static Artikel readArtikel(String artikelNummer) {
        Artikel artikel = new Artikel();
        if (getArtikelMap().containsKey(artikelNummer)) {
            artikel = getArtikelMap().get(artikelNummer);
        }
        return artikel;
    }

    /**
     * saves a article
     * @param artikel  the article to be saved
     */
    public static void saveArtikel(Artikel artikel) {
        getArtikelMap().put(artikel.getArtikelNummer(), artikel);
        writeJSON();
    }

    /**
     * reads a single publisher identified by its uuid
     * @param onlineshopURL  the identifier
     * @return onlineshop-object
     */
    public static Onlineshop readOnlineshop(String onlineshopURL) {
        Onlineshop onlineshop = new Onlineshop();
        if (getOnlineshopMap().containsKey(onlineshopURL)) {
            onlineshop = getOnlineshopMap().get(onlineshopURL);
        }
        return onlineshop;
    }

    /**
     * saves a publisher
     * @param onlineshop  the publisher to be saved
     */
    public static void saveOnlineshop(Onlineshop onlineshop) {
        getOnlineshopMap().put(onlineshop.getUrl(), onlineshop);
        writeJSON();
    }

    /**
     * gets the bookMap
     * @return the bookMap
     */
    public static Map<String, Artikel> getArtikelMap() {
        return artikelMap;
    }

    /**
     * gets the onlineshopMap
     * @return the onlineshopMap
     */
    public static Map<String, Onlineshop> getOnlineshopMap() {
        return onlineshopMap;
    }

    public static void setPublisherMap(Map<String, Onlineshop> onlineshopMap) {
        DataHandler.onlineshopMap = onlineshopMap;
    }

    /**
     * reads the articles and onlineshops
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("artikelJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Artikel[] alleArtikel = objectMapper.readValue(jsonData, Artikel[].class);
            for (Artikel artikel : alleArtikel) {
                String onlineshopURL = artikel.getOnlineshop().getUrl();
                Onlineshop onlineshop;
                if (getOnlineshopMap().containsKey(onlineshopURL)) {
                    onlineshop = getOnlineshopMap().get(onlineshopURL);
                } else {
                    onlineshop = artikel.getOnlineshop();
                    getOnlineshopMap().put(onlineshopURL, onlineshop);
                }
                artikel.setOnlineshop(onlineshop);
                getArtikelMap().put(artikel.getArtikelNummer(), artikel);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the articles and onlineshops
     *
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String bookPath = Config.getProperty("artikelJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getArtikelMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
