package com.b2s.services.order.resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("jersey")
@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.b2s.services.order");
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        // our rest resources will be available in the path /rest/*
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/rest/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registration;
    }
}
