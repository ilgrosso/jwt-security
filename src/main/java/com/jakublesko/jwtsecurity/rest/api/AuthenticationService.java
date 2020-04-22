package com.jakublesko.jwtsecurity.rest.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("authenticate")
public interface AuthenticationService {

    @POST
    Response authenticate(@QueryParam("username") String username, @QueryParam("password") String password);
}
