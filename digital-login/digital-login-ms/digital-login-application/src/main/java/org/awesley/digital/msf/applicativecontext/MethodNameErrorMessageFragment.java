package org.awesley.digital.msf.applicativecontext;

import org.aspectj.lang.Signature;

public class MethodNameErrorMessageFragment implements IErrorMessageFragment {

	@Override
	public String Create(JoinPointErrorContext jpec) {
		Signature sig = jpec.getJoinPoint().getSignature();
		return sig.getDeclaringType().getSimpleName() + "." + sig.getName();
	}

}
