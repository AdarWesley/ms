package org.awesley.digital.login;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class ContractTestsRule implements TestRule, ApplicationContextAware {

	ConfigurableApplicationContext ctx;
	
	@Override
	public Statement apply(Statement base, Description description) {
//		String testIdentifier = description.getTestClass().getName() + "." + description.getMethodName();
//		ContractTestHelper cth = 
//				BeanFactoryAnnotationUtils.qualifiedBeanOfType(ctx.getBeanFactory(), ContractTestHelper.class, testIdentifier);
//
//		return new Statement() {
//			@Override
//			public void evaluate() throws Throwable {
//				if (cth != null) {
//					description.getTestClass().getAnnotationsByType(annotationClass)
//				}
//				base.evaluate();
//			}
//		};
		return base;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = (ConfigurableApplicationContext)applicationContext;
	}

}
