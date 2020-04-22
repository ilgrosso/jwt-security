package com.jakublesko.jwtsecurity.rest.impl;

import java.util.Optional;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

@Provider
public class RestServiceExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOG = LoggerFactory.getLogger(RestServiceExceptionMapper.class);

    @Override
    public Response toResponse(final Exception ex) {
        LOG.error("Exception thrown", ex);

        ResponseBuilder builder;

        if (ex instanceof AccessDeniedException) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            builder = Response.status("anonymous".equals(authentication.getName())
//                    ? Response.Status.UNAUTHORIZED : Response.Status.FORBIDDEN);
            builder = null;
        } else {
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    header("X-Error-Info", ExceptionUtils.getRootCauseMessage(ex));
        }

        return Optional.ofNullable(builder).map(ResponseBuilder::build).orElse(null);
    }
}
