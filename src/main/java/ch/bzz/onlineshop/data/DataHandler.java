package ch.bzz.onlineshop.data;

import ch.bzz.onlineshop.model.Artikel;
import ch.bzz.onlineshop.model.Onlineshop;
import ch.bzz.onlineshop.model.User;
import ch.bzz.onlineshop.service.Config;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    private static List<User> userList = null;

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

    public static List<User> getUserList() {
        if (userList == null) {
            userList = new ArrayList<>();
            readJSON();
        }
        return userList;
    }

    public static List<Artikel> getArtikelList() {
        List<Artikel> artikelList = new ArrayList<>();

        for (Onlineshop onlineshop : getOnlineshopList()) {
            for (Artikel artikel : onlineshop.getArtikelList()) {
                artikelList.add(artikel);
            }
        }
        return artikelList;
    }

    /**
     * find the onlineshop by a article
     *
     * @param artikelNummer
     * @return
     */
    public static Onlineshop findOnlineshopByArtikelnummer(String artikelNummer) {
        for (Onlineshop onlineshop : getOnlineshopList()) {
            for (Artikel artikel : onlineshop.getArtikelList()) {
                if (artikel.getArtikelNummer().equals(artikelNummer))
                    return onlineshop;
            }
        }
        return null;
    }

    /**
     * inserts a new onlineshop
     * @param onlineshop
     */
    public static void insertOnlineshop(Onlineshop onlineshop) {
        getOnlineshopList().add(onlineshop);
        writeJSON();
    }

    /**
     * updates an existing onlineshop
     * @param onlineshop
     * @return
     */
    public static boolean updateOnlineshop(Onlineshop onlineshop) {
        boolean found = false;
        Onlineshop entry = findOnlineshopByUUID(onlineshop.getOnlineshopUUID());
        if (entry != null) {
            found = true;
            entry.setOnlineshop(onlineshop.getOnlineshop());
            writeJSON();
        }
        return found;
    }

    /**
     * deletes the user if it's available
     * @param user
     * @return
     */
    public static boolean updateUser(User user) {
        boolean found = false;
        User entry = findUserByUUID(user.getUserUUID());
        if (entry != null) {
            found = true;
            entry.setPassword(user.getPassword());
        }
        return found;
    }

    /**
     * deletes onlineshop, if it has no articles
     * @param onlineshopUUID
     * @return
     */
    public static int deleteOnlineshop(String onlineshopUUID) {
        int errorcode = 1;
        Onlineshop onlineshop = findOnlineshopByUUID(onlineshopUUID);
        if (onlineshop == null) errorcode = 1;
        else if (onlineshop.getArtikelList() == null) {
            getOnlineshopList().remove(onlineshop);
            writeJSON();
            errorcode = 0;
        } else errorcode = -1;
        return errorcode;
    }

    public static int deleteUser(String userUUID) {
        int errorcode;
        User user = findUserByUUID(userUUID);
        if (user == null) errorcode = 1;
        else if (user.getPassword() != null) {
            getUserList().remove(user);
            writeJSON();
            errorcode = 0;
        } else {
            errorcode = -1;
        }
        return errorcode;
    }

    /**
     * find the onlineshop by url
     *
     * @param uuid
     * @return
     */
    public static Onlineshop findOnlineshopByUUID(String uuid) {
        for (Onlineshop onlineshop : getOnlineshopList()) {
            for (Artikel artikel : onlineshop.getArtikelList()) {
                if (artikel.getArtikelNummer().equals(uuid))
                    return onlineshop;
            }
        }
        return null;
    }

    public static User findUserByUUID(String uuid) {
        for (User user : getUserList()) {
            if (user.getUserUUID().equals(uuid))
                return user;
        }
        return null;
    }

    /**
     * gets article by its name
     *
     * @param name
     * @return
     */
    public static Artikel findArtikelByName(String name) {
        List<Artikel> artikelList = getArtikelList();
        for (Artikel artikel : artikelList) {
            if (artikel != null && artikel.getName().equals(name)){
                return artikel;
            }
        }

        return null;
    }

    /**
     * inserts a article to the onlineshop
     * @param artikel
     * @param onlineshopURL
     * @return
     */
    public static boolean insertArtikel(Artikel artikel, String onlineshopURL) {
        Onlineshop onlineshop = findOnlineshopByUUID(onlineshopURL);
        if (onlineshop == null) {
            return false;
        } else {
            onlineshop.getArtikelList().add(artikel);
            writeJSON();
            return true;
        }
    }

    /**
     * updates a article by deleting and inserting it
     * @param artikel
     * @param onlineshopURL
     * @return
     */
    public static boolean updateArtikel(Artikel artikel, String onlineshopURL) {
        if (deleteArtikel(artikel.getArtikelNummer()))
            return insertArtikel(artikel, onlineshopURL);
        else return false;
    }

    /**
     * deletes a article identified by its articleNumber
     * @param artikelNummer
     * @return
     */
    public static boolean deleteArtikel(String artikelNummer) {
        for (Onlineshop onlineshop : getOnlineshopList()) {
            if (onlineshop.getArtikelList() != null) {
                for (Artikel artikel : onlineshop.getArtikelList()) {
                    if (artikel.getArtikelNummer().equals(artikelNummer)) {
                        onlineshop.getArtikelList().remove(artikel);
                        writeJSON();
                        return true;
                    }
                }
            }
        }
        return false;
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
     * write the onlineshops with their articles
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("onlineshopJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getOnlineshopList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    public static void insertUser(User user) {
        getUserList().add(user);
        writeJSON();
    }
}
