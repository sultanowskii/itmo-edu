package web.lab4.server.web.pointCheck;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import web.lab4.server.model.PointCheck;
import web.lab4.server.model.User;
import web.lab4.server.service.auth.JWTService;
import web.lab4.server.service.pointCheck.PointCheckService;
import web.lab4.server.service.user.UserService;
import web.lab4.server.util.JWTExtractor;
import web.lab4.server.web.auth.AuthRequired;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/api/pointChecks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PointCheckResource {
    @EJB
    private PointCheckService pointCheckService;

    @EJB
    private JWTService jwtService;

    @EJB
    private UserService userService;

    @POST
    @Path("/")
    @AuthRequired
    public Response check(@Context HttpHeaders headers, @NotNull(message="Credentials are required") @Valid Point point) {
        var user = getUserFromToken(headers);
        if (user == null) {
            return Response.status(Response.Status.FORBIDDEN).entity(errorToJSON("You must login first")).build();
        }
        var pointCheck = pointCheckService.createPointCheckForUser(point.getX(), point.getY(), point.getR(), user);
        return Response.status(Response.Status.OK).entity(pointCheck.toJSONObject()).build();
    }

    @GET
    @Path("/")
    @AuthRequired
    public Response getPreviousChecks(@Context HttpHeaders headers) {
        var user = getUserFromToken(headers);
        if (user == null) {
            return Response.status(Response.Status.FORBIDDEN).entity(errorToJSON("You must login first")).build();
        }

        var previousChecks = pointCheckService.getPreviousChecks(user);

        return Response
            .status(Response.Status.OK)
            .entity(Json.createArrayBuilder(
                    previousChecks
                    .stream()
                    .map(PointCheck::toJSONObject)
                    .toList()
                )
                .build()
            )
            .build();
    }

    @GET
    @Path("/test")
    public Response hello() {
        return Response
                .status(Response.Status.OK)
                .entity("cool!")
                .build();
    }

    public User getUserFromToken(HttpHeaders headers) {
        String token = JWTExtractor.getTokenFromHeaders(headers);
        String username = jwtService.getUsernameFromToken(token);
        return userService.getUserWithUsername(username);
    }

    private String errorToJSON(String error) {
        return Json.createObjectBuilder().add("error", error).build().toString();
    }
}
