package org.ian.mcommerce.authorization;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by jiangkun on 17/1/25.
 */
@Provider
@PreMatching
public class RestRequestFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(RestRequestFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {

        String path = requestCtx.getUriInfo().getPath();
        log.info("Filtering request path: " + path);

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());

            return;
        }

        // Then check is the service key exists and is valid.
        Authenticator demoAuthenticator = Authenticator.getInstance();
        String serviceKey = requestCtx.getHeaderString(CustomHttpHeaderNames.SERVICE_KEY);

        if (!demoAuthenticator.isServiceKeyValid(serviceKey)) {
            // Kick anyone without a valid service key
            requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

            return;
        }

        // For any pther methods besides login, the authToken must be verified
        if (!path.startsWith("login/") && !path.startsWith("register/")) {
            String authToken = requestCtx.getHeaderString(CustomHttpHeaderNames.AUTH_TOKEN);

            // if it isn't valid, just kick them out.
            if (!demoAuthenticator.isAuthTokenValid(serviceKey, authToken)) {
                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            } else {
                requestCtx.setSecurityContext(new SecurityContextImpl());
            }
        }
    }
}
