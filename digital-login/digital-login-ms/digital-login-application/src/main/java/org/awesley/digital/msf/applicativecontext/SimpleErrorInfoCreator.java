package org.awesley.digital.msf.applicativecontext;

import java.util.ArrayList;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.awesley.digital.msf.errors.ApplicationException;
import org.awesley.digital.msf.errors.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleErrorInfoCreator implements IErrorInfoCreator {

	@Autowired
	private IErrorMessageFormatter errorMessageFormatter;
	
	public SimpleErrorInfoCreator() {
	}

	@Override
	public ErrorInfo CreateErrorInfo(JoinPoint jp, Throwable ex) {
		long errorCode = 0;
		ErrorInfo innerError = null;

		if (ApplicationException.class.isAssignableFrom(ex.getClass())) {
			ApplicationException aex = (ApplicationException)ex;
			innerError = aex.getErrorInfo();
			errorCode = innerError.getErrorCode();
		}
		
		String errorMessage = errorMessageFormatter.FormatErrorMessage(jp, ex);
		return new ErrorInfo(errorCode, errorMessage, innerError);
	}
}
