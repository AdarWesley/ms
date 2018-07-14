package org.awesley.digital.login.resources.interfaces;

public interface IMapper<TargetType, SourceType> {
	TargetType mapFrom(SourceType modelEntity);
}
