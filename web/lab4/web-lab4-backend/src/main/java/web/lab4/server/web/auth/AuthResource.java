package web.lab4.server.web.auth;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.lab4.server.service.auth.AuthService;

@Stateless
@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @EJB
    private AuthService authService;

    @POST
    @Path("/register")
    public Response register(@NotNull(message="Credentials are required") @Valid Credentials credentials) {
        var result = authService.registerUser(credentials.getUsername(), credentials.getPassword());
        if (result.isSuccessful()) {
            return Response
                .status(Response.Status.CREATED)
                .entity(tokenToJSON(result.getToken()))
                .build();
        } else {
            return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(errorToJSON(result.getErrorMessage()))
                .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@NotNull(message="Credentials are required") @Valid Credentials credentials) {
        var result = authService.login(credentials.getUsername(), credentials.getPassword());
        if (result.isSuccessful()) {
            return Response
                    .status(Response.Status.OK)
                    .entity(tokenToJSON(result.getToken()))
                    .build();
        } else {
            return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(errorToJSON(result.getErrorMessage()))
                .build();
        }
    }

    @POST
    @Path("/logout")
    @AuthRequired
    public Response logout() {
        return Response
                .status(Response.Status.OK)
                .entity(tokenToJSON(""))
                .build();
    }

    @POST
    @Path("/checkToken")
    @AuthRequired
    public Response checkToken() {
        return Response
            .status(Response.Status.OK)
            .build();
    }

    private String tokenToJSON(String token) {
        return Json.createObjectBuilder().add("token", token).build().toString();
    }

    private String errorToJSON(String error) {
        return Json.createObjectBuilder().add("error", error).build().toString();
    }
}
