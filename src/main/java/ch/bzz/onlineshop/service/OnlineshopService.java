package ch.bzz.onlineshop.service;

import ch.bzz.onlineshop.data.DataHandler;
import ch.bzz.onlineshop.model.Onlineshop;

import javax.print.DocFlavor;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

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

    public Response listOnlineshop(
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        List<Onlineshop> onlineshopList = null;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            onlineshopList = DataHandler.getOnlineshopList();
        }

        Response response = Response
                .status(200)
                .entity(onlineshopList)
                .build();
        return response;
    }

    /**
     * reads a single onlineshop identified by the url
     *
     * @param uuid the onlineshopURL in the URL
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

    public Response readOnlineshop(
            @QueryParam("uuid") String uuid,
            @CookieParam("userRole") String userRole
            ) {
        Onlineshop onlineshop = null;
        int httpStatus;

        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            try {
                onlineshop = DataHandler.findOnlineshopByUUID(uuid);
                if (onlineshop != null) {
                    httpStatus = 200;
                } else {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        }
        Response response = Response
                .status(httpStatus)
                .entity(onlineshop)
                .build();
        return response;
    }

    /**
     * creates a new onlineshop without articles
     * @param onlineshopName
     * @return response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createOnlineshop(
            @FormParam("onlineshopUUID")
            @NotEmpty
            String onlineshopUUID,

            @FormParam("onlineshop")
            @NotEmpty
            @Size(min = 2, max = 40)
            String onlineshopName,

            @CookieParam("userRole") String userRole
    ) {
        List<Onlineshop> onlineshopList = DataHandler.getOnlineshopList();
        int httpStatus;
        if (userRole.equals("admin")) {
            httpStatus = 200;
            Onlineshop onlineshop = new Onlineshop();
            onlineshop.setOnlineshop(onlineshopName);
            onlineshop.setOnlineshopUUID(onlineshopUUID);
            DataHandler.insertOnlineshop(onlineshop);
        } else {
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates the onlineshop in all it's articles
     * @param onlineshopUUID
     * @param onlineshopName
     * @return response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateOnlineshop(
            @FormParam("onlineshopUUID")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String onlineshopUUID,

            @FormParam("onlineshop")
            @NotEmpty
            @Size(min = 2, max = 40)
                    String onlineshopName,

            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        Onlineshop onlineshop = new Onlineshop();
        if (userRole.equals("admin")) {
            try {
                UUID.fromString(onlineshopUUID);
                onlineshop.setOnlineshopUUID(onlineshopUUID);
                onlineshop.setOnlineshop(onlineshopName);
                if (DataHandler.updateOnlineshop(onlineshop)) {
                    httpStatus = 200;
                } else {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        } else {
            httpStatus = 403;
        }


        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing onlineshop
     * @param onlineshopUUID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteOnlineshop (
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            String onlineshopUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole.equals("admin")) {
            try {
                UUID.fromString(onlineshopUUID);
                int errorcode = DataHandler.deleteOnlineshop(onlineshopUUID);
                if (errorcode == 0) httpStatus = 200;
                else if (errorcode == -1) httpStatus = 409;
                else httpStatus = 404;
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        } else {
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * sets the attribute values of the onlineshop object
     * @param onlineshop
     * @param onlineshopUUID
     * @param name
     */
    private void setValues(
            Onlineshop onlineshop,
            String onlineshopUUID,
            String name) {
        onlineshop.setOnlineshopUUID(onlineshopUUID);
        onlineshop.setOnlineshop(name);
    }
}
