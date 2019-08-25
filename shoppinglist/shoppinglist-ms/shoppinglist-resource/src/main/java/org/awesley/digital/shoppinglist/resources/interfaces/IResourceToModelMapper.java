package org.awesley.digital.shoppinglist.resources.interfaces;

public interface IResourceToModelMapper<ResourceType, ModelType> {
	ModelType mapTo(ResourceType resourceEntity);
}
