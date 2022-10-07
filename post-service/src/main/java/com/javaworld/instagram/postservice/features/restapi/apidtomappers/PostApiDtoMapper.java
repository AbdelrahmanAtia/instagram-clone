package com.javaworld.instagram.postservice.features.restapi.apidtomappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;
import com.javaworld.instagram.postservice.features.restapi.apidtos.PostApiDto;

@Mapper(componentModel = "spring")
public interface PostApiDtoMapper {

	@Mappings({
		//TODO: add service address to the response and uncomment the following mapping
		// @Mapping(target = "serviceAddress", ignore = true)
	})
	PostApiDto entityToApi(PostEntity entity);

	@Mappings({ 
		@Mapping(target = "id", ignore = true), 
		@Mapping(target = "version", ignore = true) 
	})
	PostEntity apiToEntity(PostApiDto apiDto);

}