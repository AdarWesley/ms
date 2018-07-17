package org.awesley.digital.msf.applicativecontext;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration()
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class ApplicativeContextConfiguration {

	@Bean
	public ApplicativeContextAspect activityContextAspect() {
		return new ApplicativeContextAspect();
	}

	@Bean
	public IErrorInfoCreator errorInfoCreator() {
		return new SimpleErrorInfoCreator();
	}
	
	@Bean
	public IErrorMessageFormatter errorMessageFormatter() {
		return new DefaultErrorMessageFormatter();
	}
	
	@Bean
	@Qualifier("methodName")
	public IErrorMessageFragment methodName() {
		return new MethodNameErrorMessageFragment();
	}
	
	@Bean
	@Qualifier("contextEntities")
	public IErrorMessageFragment contextEntities() {
		return new ContextEntitiesErrorMessageFragment();
	}
	
	@Bean
	@Qualifier("innerError")
	public IErrorMessageFragment innerError() {
		return new InnerErrorMessageFragment();
	}
	
	@Bean
	@Qualifier("default")
	public IEntityErrorMessageFragment defaultEntityErrorMessageFragment() {
		return new DefaultEntityErrorMessageFragment();
	}
	
	@Bean
	public ResourceBundle errorFormatResources() {
		ResourceBundle rb = PropertyResourceBundle.getBundle("ErrorFormatResources");
		return rb;
	}
}
