package org.awesley.digital.msf.applicativecontext;

import org.aspectj.lang.JoinPoint;

public interface IErrorMessageFormatter {
	String FormatErrorMessage(JoinPoint jp, Throwable ex);
}
