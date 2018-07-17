package org.awesley.digital.msf.applicativecontext;

import org.aspectj.lang.JoinPoint;
import org.awesley.digital.msf.errors.ErrorInfo;

public interface IErrorInfoCreator {

	ErrorInfo createErrorInfo(JoinPoint jp, Throwable ex);

}