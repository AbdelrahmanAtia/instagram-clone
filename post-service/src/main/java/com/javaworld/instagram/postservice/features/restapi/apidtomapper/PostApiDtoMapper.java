package com.javaworld.instagram.postservice.features.restapi.apidtomapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.javaworld.instagram.postservice.features.restapi.apidto.PostApiDto;
import com.javaworld.instagram.postservice.features.restapi.apidto.TagApiDto;
import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.features.service.dto.Tag;

@Mapper(componentModel = "spring")
public interface PostApiDtoMapper {

	@Mappings({ @Mapping(target = "tags", source = "tags") })
	Post apiToDto(PostApiDto apiDto);

	Tag apiToDto(TagApiDto apiDto);

	List<Tag> mapToDtoList(List<TagApiDto> apiDto);

	PostApiDto mapToApiDto(Post post);
	
	
	

}