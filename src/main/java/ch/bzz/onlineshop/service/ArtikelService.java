package ch.bzz.onlineshop.service;

import ch.bzz.onlineshop.data.DataHandler;
import ch.bzz.onlineshop.model.Artikel;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * provides services for the Artikel
 * <p>
 * M133: Onlineshop
 *
 * @author Marko Micanovic
 */

@Path("artikel")
public class ArtikelService {

    /**
     * produces a list of all articles
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listArtikel(){
        List<Artikel> artikelList = DataHandler.getArtikelList();
        Response response = Response
                .status(200)
                .entity(artikelList)
                .build();
        return response;
    }

    /**
     * reads a single article identified by the artikelNummer
     *
     * @param artikelNummer the artikelNummer in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readArtikel(
            @QueryParam("artikelNummer") String artikelNummer
    ) {
        Artikel artikel = null;
        int httpStatus;

        try {
            artikel = DataHandler.findArtikelByArtikelnummer(artikelNummer);
            if (artikel != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(artikel)
                .build();
        return response;
    }

    /**
     * creates a new article
     * @param onlineshopUUID
     * @param name
     * @param preis
     * @param stueckzahl
     * @param artikelNummer
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createArtikel(
            @FormParam("onlineshopUUID")
            @NotEmpty
            String onlineshopUUID,

            @FormParam("name")
            @NotEmpty
            @Size(min = 2, max = 40)
            String name,

            @FormParam("preis")
            @NotEmpty
            @DecimalMin("0.05")
            Double preis,

            @FormParam("stueckzahl")
            @NotEmpty
            int stueckzahl,

            @FormParam("artikelNummer")
            @NotEmpty
            String artikelNummer
    ) {
       int httpStatus = 200;
       Artikel artikel = new Artikel();
       artikel.setArtikelNummer(UUID.randomUUID().toString());
       setValues(
               artikel,
               name,
               preis,
               stueckzahl,
               artikelNummer
       );

       DataHandler.insertArtikel(artikel, onlineshopUUID);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates an existing article
     * @param onlineshopUUID
     * @param name
     * @param preis
     * @param stueckzahl
     * @param artikelNummer
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateArtikel(
            @FormParam("onlineshopUUID")
            @NotEmpty
                    String onlineshopUUID,

            @FormParam("name")
            @NotEmpty
            @Size(min = 2, max = 40)
                    String name,

            @FormParam("preis")
            @NotEmpty
            @DecimalMin("0.05")
                    Double preis,

            @FormParam("stueckzahl")
            @NotEmpty
                    int stueckzahl,

            @FormParam("artikelNummer")
            @NotEmpty
                    String artikelNummer
    ) {
        int httpStatus = 200;
        try {
            UUID.fromString(artikelNummer);
            Artikel artikel = DataHandler.findArtikelByArtikelnummer(artikelNummer);
            setValues(
                    artikel,
                    name,
                    preis,
                    stueckzahl,
                    artikelNummer
            );
            if (DataHandler.updateArtikel(artikel, onlineshopUUID)) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing article by artikelNummer
     * @param artikelNummer
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteArtikel(
            @QueryParam("uuid")
            @NotEmpty
            String artikelNummer
    ) {
        int httpStatus;
        try {
            UUID.fromString(artikelNummer);

            if (DataHandler.deleteArtikel(artikelNummer)) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * sets the attribute values of the artikel object
     * @param artikel
     * @param name
     * @param preis
     * @param stueckzahl
     * @param artikelNummer
     */

    private void setValues(
            Artikel artikel,
            String name,
            Double preis,
            int stueckzahl,
            String artikelNummer) {
        artikel.setName(name);
        artikel.setPreis(preis);
        artikel.setStueckzahl(stueckzahl);
        artikel.setArtikelNummer(artikelNummer);
    }

}
