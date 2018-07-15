package org.awesley.digital.msf.applicativecontext;

import org.hibernate.event.internal.DefaultAutoFlushEventListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

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
	public IErrorMessageFragment methodNameErrorMessageFragment() {
		return new MethodNameErrorMessageFragment();
	}
	
	@Bean
	@Qualifier("contextEntities")
	public IErrorMessageFragment contextEntitiesErrorMessageFragment() {
		return new ContextEntitiesErrorMessageFragment();
	}
	
	@Bean
	@Qualifier("innerError")
	public IErrorMessageFragment innerErrorMessageFragment() {
		return new InnerErrorMessageFragment();
	}
	
	@Bean
	@Qualifier("default")
	public IEntityErrorMessageFragment defaultEntityErrorMessageFragment() {
		return new DefaultEntityErrorMessageFragment();
	}
	
	@Bean
	public MessageSource errorFormatsSource() {
		ReloadableResourceBundleMessageSource errorFormatsMessageSource = new ReloadableResourceBundleMessageSource();
		
		errorFormatsMessageSource.setBasename("ErrorFormats");
		errorFormatsMessageSource.setDefaultEncoding("UTF-8");
		
		return errorFormatsMessageSource;
	}
}
