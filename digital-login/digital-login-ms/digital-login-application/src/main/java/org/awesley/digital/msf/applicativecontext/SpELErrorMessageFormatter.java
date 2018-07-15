package org.awesley.digital.msf.applicativecontext;

import java.util.PropertyResourceBundle;

import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpELErrorMessageFormatter implements IErrorMessageFormatter {
	
	PropertyResourceBundle rb;

	@Override
	public String FormatErrorMessage(JoinPointErrorContext jpec) {
		Signature sig = jpec.getJoinPoint().getSignature();
		String methodName = sig.getDeclaringType().getSimpleName() + "." + sig.getName();
		
		ExpressionParser parser = new SpelExpressionParser();
		return null;
	}

}
