package web.lab4.server.util;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;

public class JWTExtractor {
    public static String getTokenFromContext(ContainerRequestContext containerRequestContext) {
        String authHeaderString = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        return authHeaderString != null ? authHeaderString.trim() : null;
    }

    public static String getTokenFromHeaders(HttpHeaders headers) {
        String authHeaderString = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        return authHeaderString != null ? authHeaderString.trim() : null;
    }
}
