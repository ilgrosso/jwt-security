package com.jakublesko.jwtsecurity;

import org.springframework.web.client.HttpServerErrorException;

public class PayaraAuthenticationITCase extends AbstractAuthenticationTest {

    @Override
    public String getBaseUrl() {
        return "http://localhost:8080/api/";
    }

    @Override
    protected Class<? extends Throwable> forbiddenException() {
        return HttpServerErrorException.InternalServerError.class;
    }
}
