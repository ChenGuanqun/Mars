package com.mars.config;

import com.mars.controller.SessionResource;
import com.mars.controller.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by tachen on 2017/5/11.
 */
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(UserResource.class);
        register(SessionResource.class);
    }
}
