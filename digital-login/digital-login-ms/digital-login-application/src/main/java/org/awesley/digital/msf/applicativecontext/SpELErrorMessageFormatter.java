package org.awesley.digital.msf.applicativecontext;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELErrorMessageFormatter implements IErrorMessageFormatter {
	
	@Autowired
	ResourceBundle errorFormatResources;
	
	@Override
	public String FormatErrorMessage(JoinPointErrorContext jpec) {
		String methodName = getMethodName(jpec);
		
		ExpressionParser parser = new SpelExpressionParser();
		String errorMessageExpression = getErrorMessageExpression(methodName);
		Expression expr = parser.parseExpression(errorMessageExpression);
		
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("ctx", jpec);

		return expr.getValue(context, String.class);
	}

	private String getMethodName(JoinPointErrorContext jpec) {
		Signature sig = jpec.getJoinPoint().getSignature();
		String methodName = sig.getDeclaringType().getSimpleName() + "." + sig.getName();
		return methodName;
	}

	private String getErrorMessageExpression(String methodName) {
		String errorMessageExpression;
		try {
			errorMessageExpression = errorFormatResources.getString(methodName);
		}
		catch (MissingResourceException ex) {
			errorMessageExpression = errorFormatResources.getString("DefaultErrorFormat");
		}
		
		return errorMessageExpression;
	}

}
