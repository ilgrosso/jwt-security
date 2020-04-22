package com.jakublesko.jwtsecurity;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootAuthenticationTest extends AbstractAuthenticationTest {

    @LocalServerPort
    int randomServerPort;

    @Override
    protected String getBaseUrl() {
        return "http://localhost:" + randomServerPort + "/api/";
    }
}
