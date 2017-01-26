package org.ian.mcommerce.authorization;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by jiangkun on 17/1/25.
 */
@Provider
@PreMatching
public class RestResponseFilter implements ContainerResponseFilter {
    private HashMap<Integer, String> httpStatusMessage = new HashMap<>();
    private final static Logger log = Logger.getLogger(RestResponseFilter.class.getName());

    public RestResponseFilter() {
        httpStatusMessage.put(Response.Status.UNAUTHORIZED.getStatusCode(), "请重新登陆");
        httpStatusMessage.put(Response.Status.FORBIDDEN.getStatusCode(), "无权访问");
    }

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {
        log.info("Filtering REST Response");

        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");    // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", CustomHttpHeaderNames.SERVICE_KEY + ", " + CustomHttpHeaderNames.AUTH_TOKEN);
        if (httpStatusMessage.containsKey(responseCtx.getStatus())) {
            responseCtx.setEntity(httpStatusMessage.get(responseCtx.getStatus()));
        }

    }
}


