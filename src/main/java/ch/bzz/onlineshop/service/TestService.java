package ch.bzz.onlineshop.service;

//import ch.bzz.onlineshop.data.DataHandler;
import ch.bzz.onlineshop.model.Artikel;
import ch.bzz.onlineshop.model.Onlineshop;
import ch.bzz.onlineshop.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

/**
 * short description
 * <p>
 * Bookshelf
 *
 * @author TODO
 */
@Path("test")
public class TestService {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();
    }
}