package org.awesley.digital.login.resources.interfaces;

public interface IResourceFromModelMapper<ResourceType, ModelType> {
	ResourceType mapFrom(ModelType modelEntity);
}
