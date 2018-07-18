package org.awesley.digital.msf.applicativecontext.entity;

import org.awesley.digital.msf.applicativecontext.entity.IEntityErrorMessageFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractEntityErrorMessageFragment implements IEntityErrorMessageFragment {

	@Autowired
	@Qualifier("default")
	IEntityErrorMessageFragment defaultEntityErrorMessageFragment;
	
	@Override
	public String create(String entityType, long entityId) {
		return defaultEntityErrorMessageFragment.create(entityType, entityId);
	}
	
	public abstract String getEntityType();

	public String create(long entityId) {
		return create(getEntityType(), entityId);
	}
}
