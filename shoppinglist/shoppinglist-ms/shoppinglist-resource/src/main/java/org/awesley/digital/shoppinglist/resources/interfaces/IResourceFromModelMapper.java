package org.awesley.digital.shoppinglist.resources.interfaces;

public interface IResourceFromModelMapper<ResourceType, ModelType> {

	ResourceType mapFrom(ModelType modelEntity);

}
