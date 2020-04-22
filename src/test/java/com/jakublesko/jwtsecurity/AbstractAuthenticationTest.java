package com.jakublesko.jwtsecurity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Base64;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.jupiter.api.Test;

public abstract class AbstractAuthenticationTest {

    protected abstract String getBaseUrl();

    @Test
    public void anonymous() {
        Response response = WebClient.create(getBaseUrl()).path("public").get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Hello from public API controller", response.readEntity(String.class));

        response = WebClient.create(getBaseUrl()).path("private").path("user").get();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

        response = WebClient.create(getBaseUrl()).path("private").path("admin").get();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    private void authenticated(final String whoyes, final String whonot, final String authorization) {
        Response response = WebClient.create(getBaseUrl()).path("private").path(whoyes).
                header(HttpHeaders.AUTHORIZATION, authorization).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Hello from private API controller for " + whoyes.toUpperCase(), response.readEntity(String.class));

        response = WebClient.create(getBaseUrl()).path("private").path(whonot).
                header(HttpHeaders.AUTHORIZATION, authorization).get();
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }

    private String basic(final String who) {
        return "Basic " + Base64.getEncoder().encodeToString((who + ":password").getBytes());
    }

    @Test
    public void basic_user() {
        authenticated("user", "admin", basic("user"));
    }

    @Test
    public void basic_admin() {
        authenticated("admin", "user", basic("admin"));
    }

    private String jwt(final String who) {
        Response response = WebClient.create(getBaseUrl()).path("authenticate").
                query("username", who).query("password", "password").post(null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        String authorization = response.getHeaderString(HttpHeaders.AUTHORIZATION);
        assertNotNull(authorization);

        return authorization;
    }

    @Test
    public void jwt_user() {
        authenticated("user", "admin", jwt("user"));
    }

    @Test
    public void jwt_admin() {
        authenticated("admin", "user", jwt("admin"));
    }
}
