package org.awesley.digital.usergroup.resources.interfaces;

public interface IResourceToModelMapper<ResourceType, ModelType> {
	ModelType mapTo(ResourceType resourceEntity);
}
