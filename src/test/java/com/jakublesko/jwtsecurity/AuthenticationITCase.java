package com.jakublesko.jwtsecurity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class AuthenticationITCase {

    private static final String BASE_URL = "http://localhost:8080/api/";

    @Test
    public void anonymous() {
        ResponseEntity<String> response = new RestTemplate().getForEntity(BASE_URL + "public", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello from public API controller", response.getBody());
    }

    private void authenticated(final String whoyes, final String whonot, final String authorization) {
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(HttpHeaders.AUTHORIZATION, authorization);
        HttpEntity<String> entity = new HttpEntity<>(null, reqHeaders);

        ResponseEntity<String> response =
                new RestTemplate().exchange(BASE_URL + "private/" + whoyes, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello from private API controller for " + whoyes.toUpperCase(), response.getBody());

        assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> new RestTemplate().exchange(BASE_URL + "private/" + whonot, HttpMethod.GET, entity, String.class));
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
        ResponseEntity<String> response = new RestTemplate().postForEntity(
                BASE_URL + "authenticate?username={u}&password={p}", null, String.class, who, "password");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String authorization = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
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
