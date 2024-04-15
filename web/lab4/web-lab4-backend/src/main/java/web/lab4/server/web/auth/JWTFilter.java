package web.lab4.server.web.auth;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import jakarta.ws.rs.core.Response.Status;
import web.lab4.server.service.auth.JWTService;
import web.lab4.server.util.JWTExtractor;

@AuthRequired
@Priority(Priorities.AUTHENTICATION)
@Provider
public class JWTFilter implements ContainerRequestFilter {
    @EJB
    JWTService jwtService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String token = JWTExtractor.getTokenFromContext(containerRequestContext);

        if (token == null || token.isEmpty()) {
            containerRequestContext.abortWith(
                    Response.status(Status.FORBIDDEN).entity(errorToJSON("You must login first")).build()
            );
            return;
        }

        String username = jwtService.getUsernameFromToken(token);

        if (username == null || username.isEmpty()) {
            containerRequestContext.abortWith(
                Response.status(Status.FORBIDDEN).entity(errorToJSON("You must login first")).build()
            );
            return;
        }

        containerRequestContext.getHeaders().add("username", username);
    }

    private String errorToJSON(String error) {
        return Json.createObjectBuilder().add("error", error).build().toString();
    }
}
