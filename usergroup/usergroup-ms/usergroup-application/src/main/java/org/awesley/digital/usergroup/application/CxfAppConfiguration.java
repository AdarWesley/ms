package org.awesley.digital.usergroup.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.apache.cxf.spring.boot.autoconfigure.CxfProperties;
import org.apache.cxf.transport.http.HttpDestinationFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.transport.servlet.ServletDestinationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
public class CxfAppConfiguration {
	@Autowired
	private ApplicationContext ctx;

	@Autowired
    private Bus bus;

	@Bean
	@Primary
	public CxfProperties cxfProperties() {
		CxfProperties props = new CxfProperties();
		props.setPath("/");
		return props;
	}
	
    @Bean
    public Server rsServer() {
    	ServletDestinationFactory servletDestinationFactory = new ServletDestinationFactory();
    	bus.setExtension(servletDestinationFactory, HttpDestinationFactory.class);
    	
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/usergroup-service");

		List<Object> providers = new ArrayList<Object>(ctx.getBeansWithAnnotation(Provider.class).values());
		providers.add(jsonProvider(objectMapper()));
        endpoint.setProviders(providers);
        
        List<Object> serviceBeans = new ArrayList<Object>();
        serviceBeans.addAll(ctx.getBeansWithAnnotation(Path.class).values());
        
        endpoint.setServiceBeans(serviceBeans);
        
        endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        
        Server server = endpoint.create();
        
        return server;
    }

	@Bean
	@ConditionalOnMissingBean
	public JacksonJsonProvider jsonProvider(ObjectMapper objectMapper) {
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(objectMapper);
		return provider;
	}

	@Bean
	@ConditionalOnMissingBean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
