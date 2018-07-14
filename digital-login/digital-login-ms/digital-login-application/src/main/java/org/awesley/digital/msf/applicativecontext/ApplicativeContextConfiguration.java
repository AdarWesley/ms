package org.awesley.digital.msf.applicativecontext;

import org.hibernate.event.internal.DefaultAutoFlushEventListener;
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
	
}
