package org.awesley.digital.msf.applicativecontext;

public class DefaultEntityErrorMessageFragment implements IEntityErrorMessageFragment {

	@Override
	public String Create(JoinPointErrorContext jpec, ContextEntityInfo entityInfo) {
		return String.format("%1$s[%2$d]", entityInfo.getEntityType(), entityInfo.getId());
	}

}
