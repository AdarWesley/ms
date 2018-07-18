package org.awesley.digital.msf.applicativecontext.entity;

public class UserEntityMessageFragment {
	public String create(Object entityId) {
		String messageFragment = String.format("User[%1$d]", (long)entityId);
		return messageFragment;
	}
}
