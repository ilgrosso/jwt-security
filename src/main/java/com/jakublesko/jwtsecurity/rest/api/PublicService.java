package com.jakublesko.jwtsecurity.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("public")
public interface PublicService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getMessage();
}
