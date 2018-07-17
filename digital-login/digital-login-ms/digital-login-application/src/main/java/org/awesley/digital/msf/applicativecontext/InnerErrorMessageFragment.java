package org.awesley.digital.msf.applicativecontext;

import org.awesley.digital.msf.errors.ApplicationException;
import org.awesley.digital.msf.errors.ErrorInfo;

public class InnerErrorMessageFragment implements IErrorMessageFragment {

	@Override
	public String create(JoinPointErrorContext jpec) {
		if (ApplicationException.class.isAssignableFrom(jpec.getException().getClass())) {
			ApplicationException aex = (ApplicationException)jpec.getException();
			ErrorInfo innerErrorInfo = aex.getErrorInfo();
			if (innerErrorInfo != null) {
				return innerErrorInfo.getMessage();
			}
		}
		return jpec.getException().getMessage();
	}

}
