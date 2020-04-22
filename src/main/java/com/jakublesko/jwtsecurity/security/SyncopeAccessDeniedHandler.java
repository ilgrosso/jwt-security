package com.jakublesko.jwtsecurity.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

public class SyncopeAccessDeniedHandler extends AccessDeniedHandlerImpl {

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
            final AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.addHeader("X-Error-Info", accessDeniedException.getMessage());

        super.handle(request, response, accessDeniedException);
    }
}
