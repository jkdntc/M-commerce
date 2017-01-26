package org.ian.mcommerce.authorization;

import org.glassfish.jersey.message.filtering.SecurityEntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/authorize")
public class AuthorizationApplication extends ResourceConfig {
    public AuthorizationApplication() {
        packages("org.ian.mcommerce");
        register(SecurityEntityFilteringFeature.class);
    }
}

//public class AuthorizationApplication extends Application {
//    public AuthorizationApplication() {
//        System.out.println("A");
//    }
//}