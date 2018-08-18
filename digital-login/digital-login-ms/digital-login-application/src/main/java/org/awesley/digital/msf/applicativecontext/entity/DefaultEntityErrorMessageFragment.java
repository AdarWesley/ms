package org.awesley.digital.msf.applicativecontext.entity;

public class DefaultEntityErrorMessageFragment implements IEntityErrorMessageFragment {

	public String create(String entityType, long entityId) {
		return String.format("%1$s[%2$d]", entityType, entityId);
	}

}
