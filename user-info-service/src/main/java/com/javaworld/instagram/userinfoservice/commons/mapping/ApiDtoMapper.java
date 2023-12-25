package com.javaworld.instagram.userinfoservice.commons.mapping;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface ApiDtoMapper<A, D> {
	
    A toApiDto(D dto);

    D toDto(A apiDto);

    List<A> toApiDtoList(List<D> dtoList);

    List<D> toDtoList(List<A> apiDtoList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget D dto, A apiDto);
}
