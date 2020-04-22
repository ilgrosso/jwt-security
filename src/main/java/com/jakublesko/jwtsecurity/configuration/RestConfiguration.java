package com.jakublesko.jwtsecurity.configuration;

import com.jakublesko.jwtsecurity.rest.impl.RestServiceExceptionMapper;
import java.util.List;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.spring.JAXRSServerFactoryBeanDefinitionParser.SpringJAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("org.apache.syncope.core.rest.cxf.service")
@Configuration
public class RestConfiguration {

    @Autowired
    private Bus bus;

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public RestServiceExceptionMapper restServiceExceptionMapper() {
        return new RestServiceExceptionMapper();
    }

    @Bean
    public Server restContainer() {
        SpringJAXRSServerFactoryBean restContainer = new SpringJAXRSServerFactoryBean();
        restContainer.setBus(bus);
        restContainer.setAddress("/");
        restContainer.setStaticSubresourceResolution(true);
        restContainer.setBasePackages(List.of(
                "com.jakublesko.jwtsecurity.rest.api",
                "com.jakublesko.jwtsecurity.rest.impl"));

        restContainer.setProviders(List.of(restServiceExceptionMapper()));

        restContainer.setApplicationContext(ctx);
        return restContainer.create();
    }
}
