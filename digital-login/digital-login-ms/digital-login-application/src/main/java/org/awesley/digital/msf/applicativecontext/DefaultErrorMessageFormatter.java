package org.awesley.digital.msf.applicativecontext;

import java.util.ArrayList;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.awesley.digital.msf.errors.ApplicationException;
import org.awesley.digital.msf.errors.ErrorInfo;

public class DefaultErrorMessageFormatter implements IErrorMessageFormatter {

	@Override
	public String FormatErrorMessage(JoinPoint jp, Throwable ex) {
		String message = String.format("While executing %1$s on %2$s received error: %3$s", 
				GetMethodName(jp), ContextEntities(), GetInnerErrorMessage(ex));
		return message;
	}

	private String GetMethodName(JoinPoint jp) {
		Signature sig = jp.getSignature();
		return sig.getDeclaringType().getSimpleName() + "." + sig.getName();
	}
	
	private String ContextEntities() {
		Collection<ArrayList<ContextEntityInfo>> contextEntities = ApplicativeContextEntities.GetContextEntities();
		if (contextEntities == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (ArrayList<ContextEntityInfo> list : contextEntities) {
			for (ContextEntityInfo entityInfo : list) {
				sb.append(EntityInfoMessage(entityInfo));
			}
		}
		
		return sb.toString();
	}

	private String EntityInfoMessage(ContextEntityInfo entityInfo) {
		return String.format("%1$s[%2$d]", entityInfo.getEntityType(), entityInfo.getId());
	}

	private String GetInnerErrorMessage(Throwable ex) {
		if (ApplicationException.class.isAssignableFrom(ex.getClass())) {
			ApplicationException aex = (ApplicationException)ex;
			ErrorInfo innerErrorInfo = aex.getErrorInfo();
			if (innerErrorInfo != null) {
				return innerErrorInfo.getMessage();
			}
		}
		return ex.getMessage();
	}
}
