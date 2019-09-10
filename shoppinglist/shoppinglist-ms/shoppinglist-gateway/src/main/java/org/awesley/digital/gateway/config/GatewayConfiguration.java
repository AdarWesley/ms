package org.awesley.digital.gateway.config;

import java.util.ArrayList;
import java.util.Collection;

import org.awesley.digital.gateway.implementation.GroupGatewayService;
import org.awesley.digital.shoppinglist.service.interfaces.gateway.IGroupGatewayService;
import org.awesley.digital.usergroup.resources.interfaces.UserGroupApiConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import feign.Feign;
import feign.RequestInterceptor;

@Configuration
@Import(value = { UserGroupApiConfig.class })
public class GatewayConfiguration {
	
	@Bean
	public IGroupGatewayService groupGatewayService() {
		return new GroupGatewayService();
	}
	
	@Bean
	public Collection<RequestInterceptor> requestInterceptors() {
		return new ArrayList<RequestInterceptor>();
	}
	
	@Bean
	public Feign.Builder feignBuilder(){
		return new Feign.Builder();
	}
}
