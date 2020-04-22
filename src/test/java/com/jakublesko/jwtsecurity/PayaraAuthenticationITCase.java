package com.jakublesko.jwtsecurity;

public class PayaraAuthenticationITCase extends AbstractAuthenticationTest {

    @Override
    public String getBaseUrl() {
        return "http://localhost:8080/api/";
    }
}
