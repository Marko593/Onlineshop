package ch.bzz.onlineshop.service;

import ch.bzz.onlineshop.data.DataHandler;
import ch.bzz.onlineshop.model.Artikel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

/**
 * provides services for the Onlineshop
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
}
