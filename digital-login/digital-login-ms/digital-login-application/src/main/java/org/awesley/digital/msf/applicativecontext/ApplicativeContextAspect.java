package org.awesley.digital.msf.applicativecontext;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.awesley.digital.msf.errors.ApplicationException;
import org.awesley.digital.msf.errors.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class ApplicativeContextAspect {
	
	private static IErrorInfoCreator errorInfoCreator;
	
	public IErrorInfoCreator getErrorInfoCreator() {
		return errorInfoCreator;
	}

	@Autowired
	public void setErrorInfoCreator(IErrorInfoCreator errorInfoCreator) {
		ApplicativeContextAspect.errorInfoCreator = errorInfoCreator;
	}

	public ApplicativeContextAspect() {
		super();
	}
	
	@Pointcut("execution(* org.awesley.digital..resources.implementation.delegate..*(..))") 
	public void resourceDelegate(){
		
	}
	
	@Before("resourceDelegate()")
	public void beforeResourceDelegate(JoinPoint jp) {
		System.out.println("XXXXX: " + jp.getSignature().getDeclaringTypeName() + " XXXXX");
		ApplicativeContextEntities.Clear();
	}
	
	@AfterThrowing(pointcut = "resourceDelegate()", throwing = "ex")
	public void afterThrowingResourceDelegate(JoinPoint jp, Throwable ex) {
		ErrorInfo errorInfo = errorInfoCreator.CreateErrorInfo(jp, ex);
		ApplicativeContextEntities.Clear();
		throw new ApplicationException(errorInfo, ex);
	}

	@AfterReturning(pointcut = "resourceDelegate()")
	public void afterResourceDelegate(JoinPoint jp) {
		ApplicativeContextEntities.Clear();
	}
}
