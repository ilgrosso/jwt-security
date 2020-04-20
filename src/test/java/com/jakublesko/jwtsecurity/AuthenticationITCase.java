package com.jakublesko.jwtsecurity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    private void authenticated(final String whoyes, final String whonot) {
        ResponseEntity<String> response = new RestTemplate().postForEntity(
                BASE_URL + "authenticate?username={u}&password={p}", null, String.class, whoyes, "password");
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String authorization = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assertNotNull(authorization);

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.add(HttpHeaders.AUTHORIZATION, authorization);
        HttpEntity<String> entity = new HttpEntity<>(null, reqHeaders);

        response = new RestTemplate().exchange(BASE_URL + "private/" + whoyes, HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello from private API controller for " + whoyes.toUpperCase(), response.getBody());

        assertThrows(HttpServerErrorException.InternalServerError.class,
                () -> new RestTemplate().exchange(BASE_URL + "private/" + whonot, HttpMethod.GET, entity, String.class));
    }

    @Test
    public void user() {
        authenticated("user", "admin");
    }

    @Test
    public void admin() {
        authenticated("admin", "user");
    }
}
