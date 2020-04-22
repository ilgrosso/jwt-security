package com.jakublesko.jwtsecurity.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("private")
public interface PrivateService {

    @GET
    @Path("user")
    @Produces(MediaType.TEXT_PLAIN)
    String getUserMessage();

    @GET
    @Path("admin")
    @Produces(MediaType.TEXT_PLAIN)
    String getAdminMessage();
}
