package org.ian.mcommerce.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.security.auth.login.LoginException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.GeneralSecurityException;

//import javax.ejb.Stateless;

/**
 * Created by jiangkun on 17/1/25.
 */
//@Stateless( name = "LoginLogoutResource", mappedName = "ejb/LoginLogoutResource" )
@DeclareRoles({"Administrator", "Manager", "Employee"})
@RequestScoped
@Path("/")
public class LoginLogoutResource {

    private static final long serialVersionUID = -6663599014192066936L;
    @Context
    SecurityContext securityContext;

    @PermitAll
    @POST
    @Path("login")
    public Response login(
            @Context HttpHeaders httpHeaders,
            @FormParam("username") String username,
            @FormParam("password") String password) {

        Authenticator demoAuthenticator = Authenticator.getInstance();
        String serviceKey = httpHeaders.getHeaderString(CustomHttpHeaderNames.SERVICE_KEY);

        try {
            String authToken = demoAuthenticator.login(serviceKey, username, password);

            ObjectMapper jsonObjBuilder = new ObjectMapper();
            ObjectNode objectNode = jsonObjBuilder.createObjectNode();
            objectNode.put("auth_token", authToken);

            return getNoCacheResponseBuilder(Response.Status.OK).entity(objectNode.toString()).build();

        } catch (final LoginException ex) {
            ObjectMapper jsonObjBuilder = new ObjectMapper();
            ObjectNode objectNode = jsonObjBuilder.createObjectNode();
            objectNode.put("message", "Problem matching service key, username and password");
            return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity(objectNode.toString()).build();
        }
    }

    @GET
    @Path("/demogetmethod")
    @RolesAllowed("Manager")
    public Response demoGetMethod() {
        ObjectMapper jsonObjBuilder = new ObjectMapper();
        ObjectNode objectNode = jsonObjBuilder.createObjectNode();
        objectNode.put("message", "Executed demoGetMethod");
        return getNoCacheResponseBuilder(Response.Status.OK).entity(objectNode.toString()).build();
    }

    @RolesAllowed("Administrator")
    @POST
    @Path("demopostmethod")
    @Produces(MediaType.APPLICATION_JSON)
    public Response demoPostMethod() {
        boolean is = securityContext.isUserInRole("Administrator");
        ObjectMapper jsonObjBuilder = new ObjectMapper();
        ObjectNode objectNode = jsonObjBuilder.createObjectNode();
        objectNode.put("message", "Executed demoPostMethod");
        return getNoCacheResponseBuilder(Response.Status.ACCEPTED).entity(objectNode.toString()).build();
    }

    @PermitAll
    @POST
    @Path("logout")
    public Response logout(
            @Context HttpHeaders httpHeaders) {
        try {
            Authenticator demoAuthenticator = Authenticator.getInstance();
            String serviceKey = httpHeaders.getHeaderString(CustomHttpHeaderNames.SERVICE_KEY);
            String authToken = httpHeaders.getHeaderString(CustomHttpHeaderNames.AUTH_TOKEN);

            demoAuthenticator.logout(serviceKey, authToken);

            return getNoCacheResponseBuilder(Response.Status.NO_CONTENT).build();
        } catch (final GeneralSecurityException ex) {
            return getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setMaxAge(-1);
        cc.setMustRevalidate(true);

        return Response.status(status).cacheControl(cc);
    }
}
