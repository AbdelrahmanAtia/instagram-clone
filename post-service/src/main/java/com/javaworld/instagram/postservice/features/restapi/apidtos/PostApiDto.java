package com.javaworld.instagram.postservice.features.restapi.apidtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class PostApiDto {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("title")
	private String title;

	@JsonProperty("userId")
	private Integer userId;

	@JsonProperty("tags")
	@Valid
	private List<TagApiDto> tags = null;

	@JsonProperty("serviceAddress")
	private String serviceAddress;

	public PostApiDto id(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * Get id
	 * 
	 * @return id
	 */

	@Schema(name = "id", required = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PostApiDto title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Get title
	 * 
	 * @return title
	 */

	@Schema(name = "title", required = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PostApiDto userId(Integer userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * Get userId
	 * 
	 * @return userId
	 */

	@Schema(name = "userId", required = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public PostApiDto tags(List<TagApiDto> tags) {
		this.tags = tags;
		return this;
	}

	public PostApiDto addTagsItem(TagApiDto tagsItem) {
		if (this.tags == null) {
			this.tags = new ArrayList<>();
		}
		this.tags.add(tagsItem);
		return this;
	}

	/**
	 * Get tags
	 * 
	 * @return tags
	 */
	@Valid
	@Schema(name = "tags", required = false)
	public List<TagApiDto> getTags() {
		return tags;
	}

	public void setTags(List<TagApiDto> tags) {
		this.tags = tags;
	}

	public PostApiDto serviceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
		return this;
	}

	/**
	 * Get serviceAddress
	 * 
	 * @return serviceAddress
	 */

	@Schema(name = "serviceAddress", required = false)
	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		PostApiDto post = (PostApiDto) o;
		return Objects.equals(this.id, post.id) && Objects.equals(this.title, post.title)
				&& Objects.equals(this.userId, post.userId) && Objects.equals(this.tags, post.tags)
				&& Objects.equals(this.serviceAddress, post.serviceAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, userId, tags, serviceAddress);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PostApiDto {\n");
		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    title: ").append(toIndentedString(title)).append("\n");
		sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
		sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
		sb.append("    serviceAddress: ").append(toIndentedString(serviceAddress)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
