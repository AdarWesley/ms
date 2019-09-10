package org.awesley.digital.usergroup.resources.interfaces;

public interface IResourceFromModelMapper<ResourceType, ModelType> {

	ResourceType mapFrom(ModelType modelEntity);

}
