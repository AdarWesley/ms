package org.awesley.digital.login.resources.interfaces;

public interface IResourceToModelMapper<ResourceType, ModelType> {
	ModelType mapTo(ResourceType resourceEntity);
}
