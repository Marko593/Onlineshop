package ch.bzz.onlineshop.service;

import ch.bzz.onlineshop.data.DataHandler;
import ch.bzz.onlineshop.model.Artikel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for the bookshelf
 *
 * M133: Bookshelf
 *
 * @Author Marko Micanovic
 */

@Path("artikel")
public class ArtikelServiceFail {

    /**
     * produces a map of all books
     * @return
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listArtikel() {
        Map<String, Artikel> artikelMap = DataHandler.getArtikelMap();

        Response response = Response
                .status(200)
                .entity(artikelMap)
                .build();
        return response;
    }

    /**
     * reads a single article identified by the articleNumber
     *
     * @param artikelNummer
     * @return
     */

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readArtikel(
            @QueryParam("artikelnummer") String artikelNummer
    ){
        Artikel artikel = null;
        int httpStatus;

//        try {
//            UUID.fromString(artikelNummer);
            artikel = DataHandler.readArtikel(artikelNummer);
            if (artikel.getName() == null){
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
//        } catch (IllegalArgumentException argEx) {
//            httpStatus = 400;
//        }

        Response response = Response
                .status(200)
                .entity(artikel)
                .build();
        return response;

    }
}
