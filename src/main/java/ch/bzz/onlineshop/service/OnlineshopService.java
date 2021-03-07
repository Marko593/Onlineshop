package ch.bzz.onlineshop.service;

import ch.bzz.onlineshop.data.DataHandler;
import ch.bzz.onlineshop.model.Artikel;
import ch.bzz.onlineshop.model.Onlineshop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * provides services for the Onlineshop
 * <p>
 * M133: Onlineshop
 *
 * @author Marko Micanovic
 */

@Path("onlineshop")
public class OnlineshopService {

    /**
     * produces a list of all onlineshops
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public Response listOnlineshop(){
        List<Onlineshop> onlineshopList = DataHandler.getOnlineshopList();
        Response response = Response
                .status(200)
                .entity(onlineshopList)
                .build();
        return response;
    }

    /**
     * reads a single onlineshop identified by the url
     *
     * @param url the onlineshopURL in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readOnlineshop(
            @QueryParam("url") String url
    ) {
        Onlineshop onlineshop = null;
        int httpStatus;

        try {
            onlineshop = DataHandler.findOnlineshopByURL(url);
            if (onlineshop != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(onlineshop)
                .build();
        return response;
    }
}
