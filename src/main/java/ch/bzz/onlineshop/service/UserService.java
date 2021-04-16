package ch.bzz.onlineshop.service;

import ch.bzz.onlineshop.data.DataHandler;
import ch.bzz.onlineshop.data.UserData;
import ch.bzz.onlineshop.model.User;
import org.w3c.dom.UserDataHandler;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("user")
public class UserService {

    /**
     * creates a new user
     * @param password
     * @param userName
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createArtikel(

            @FormParam("password")
            @NotEmpty
            @Size(min = 2, max = 20)
                    String password,

            @FormParam("userName")
            @NotEmpty
            @Size(min = 2, max = 40)
                    String userName
    ) {
        int httpStatus = 200;
        User user = new User();
        user.setUserUUID(UUID.randomUUID().toString());
        setValues(
                user,
                password,
                userName
        );

        DataHandler.insertUser(user);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates an existing user
     * @param userUUID
     * @param password
     * @return response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUser(
            @FormParam("userUUID")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String userUUID,

            @FormParam("password")
            @NotEmpty
            @Size(min = 2, max = 40)
                    String password
    ) {
        int httpStatus;
        User user = new User();
        try {
            UUID.fromString(userUUID);
            user.setUserUUID(userUUID);
            user.setPassword(password);
            if (DataHandler.updateUser(user)) {
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
     * deletes an existing user
     * @param userUUID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteOnlineshop (
            @QueryParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String userUUID
    ) {
        int httpStatus;
        try {
            UUID.fromString(userUUID);
            int errorcode = DataHandler.deleteUser(userUUID);
            if (errorcode == 0) httpStatus = 200;
            else if (errorcode == -1) httpStatus = 409;
            else httpStatus = 404;
        } catch (IllegalArgumentException argEx) {
            httpStatus = 400;
        }
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ) {
        int httpStatus;

        User user = UserData.findUser(username, password);
        if (user.getRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

    /**
     * logout current user
     *
     * @return Response object with guest-Cookie
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {

        Response response = Response
                .status(200)
                .entity("")
                .build();
        return response;
    }

    private void setValues(
            User user,
            String password,
            String userName) {
        user.setPassword(password);
        user.setUserName(userName);
    }
}
