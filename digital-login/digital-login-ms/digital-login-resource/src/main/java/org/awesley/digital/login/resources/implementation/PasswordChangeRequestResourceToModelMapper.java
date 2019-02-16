package org.awesley.digital.login.resources.implementation;

import org.awesley.digital.login.resources.interfaces.IResourceToModelMapper;
import org.awesley.digital.login.resources.models.PasswordChangeRequest;

public class PasswordChangeRequestResourceToModelMapper implements
		IResourceToModelMapper<PasswordChangeRequest, org.awesley.digital.login.service.model.PasswordChangeRequest> {

	@Override
	public org.awesley.digital.login.service.model.PasswordChangeRequest mapTo(PasswordChangeRequest resourceEntity) {
		org.awesley.digital.login.service.model.PasswordChangeRequest modelEntity = 
				new org.awesley.digital.login.service.model.PasswordChangeRequest();
		modelEntity.setOldPassword(resourceEntity.getOldpassword());
		modelEntity.setNewPassword(resourceEntity.getNewpassword());
		
		return modelEntity;
	}

}
