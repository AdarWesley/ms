package org.awesley.digital.shoppinglist.service.interfaces.gateway;

import org.awesley.digital.shoppinglist.service.model.GroupRef;

public interface IGroupGatewayService {

	GroupRef getGroup(String groupName);

}
