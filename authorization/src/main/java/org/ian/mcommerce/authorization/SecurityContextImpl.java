package org.ian.mcommerce.authorization;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by jiangkun on 17/1/25.
 */
@RequestScoped
public class SecurityContextImpl implements SecurityContext {
    public SecurityContextImpl() {
        System.out.println("SecurityContext inited");
    }

    @Override
    public Principal getUserPrincipal() {
        return new Principal() {
            @Override
            public String getName() {
                return "Jersey";
            }
        };
    }

    @Override
    public boolean isUserInRole(final String role) {
        return "Manager".equals(role);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }

    @PreDestroy
    public void destory() {
        System.out.println("SecurityContext destoried");
    }
}
