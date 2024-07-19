package com.javaworld.instagram.postservice.features.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.commons.utils.SecurityLoggingUtil;
import com.javaworld.instagram.postservice.commons.utils.SecurityUtil;
import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;
import com.javaworld.instagram.postservice.features.persistence.entities.TagEntity;
import com.javaworld.instagram.postservice.features.persistence.repositories.PostRepository;
import com.javaworld.instagram.postservice.features.persistence.repositories.TagRepository;
import com.javaworld.instagram.postservice.features.service.dto.Comment;
import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.features.service.dtomapper.PostMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Service
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private HttpServletResponse httpServletResponse;
	
	@Override
	@Transactional
	public Post createPost(Post post) {

		try {

			logger.info("createPost: creates a new post");

			SecurityLoggingUtil.logAuthorizationInfo(getSecurityContext()); // TODO: useful utility function was found in the
																	// book
			PostEntity postEntity = postMapper.dtoToEntity(post);
			
			postEntity.setUserUuid(SecurityUtil.getUserUuidFromAccessToken(getSecurityContext()));			
			List<TagEntity> tags = extractAndSaveTags(post.getCaption());
			assignTagsToPost(tags, postEntity);

			postRepository.save(postEntity);

			Post savedPost = postMapper.entityToDto(postEntity);

			logger.info("createPost: created a post entity");

			return savedPost;

		} catch (DataIntegrityViolationException dive) {
			throw new InvalidInputException(dive.getMessage(), dive);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Post> getPosts(UUID userUuid, int pageNumber, int pageSize) {
		logger.info("Will get posts page={} for userUuid={}", userUuid, pageNumber);

				
		Pageable pageable = PageRequest.of(
				pageNumber, 
				pageSize, 
				Sort.by("createdAt").descending());

		Page<PostEntity> entityPage = postRepository.findByUserUuid(userUuid, pageable);

		httpServletResponse.setHeader("page_size", String.valueOf(pageable.getPageSize()));
		httpServletResponse.setHeader("page_number", String.valueOf(pageable.getPageNumber()));

		return entityPage.map(postMapper::entityToDto);
	}

	@Override
	@Transactional(readOnly = true)
	public int countPosts(UUID userUuid) {
		return postRepository.countByUserUuid(userUuid);
	}
	
	@Override
	@Transactional
	public int deletePosts(List<String> postStrUuids) {

		logger.info("deletePosts: tries to delete posts with uuids {}", postStrUuids);
		
		//TODO: delete posts images from file system

		return postRepository.deleteByPostUuidIn(postMapper.mapStrUuidToUuidObj(postStrUuids));

	}
	
	@Override
	@Transactional
    public void deletePostsByUserUuid(UUID userUuid) {
		//TODO: delete posts images from file system

		postRepository.deleteByUserUuid(userUuid);	
	}

	private SecurityContext getSecurityContext() {
		return SecurityContextHolder.getContext();
	}
	
	private void assignTagsToPost(List<TagEntity> tags, PostEntity postEntity) {
		tags.forEach(t -> postEntity.addPostTagAssignment(t));
	}

	private List<TagEntity> extractAndSaveTags(String caption) {

		List<TagEntity> savedTags = new ArrayList<>();
		List<TagEntity> tagEntityList = extractHashtags(caption);

		tagEntityList.stream().forEach(t -> {
			tagRepository.findByName(t.getName()).ifPresentOrElse(e -> savedTags.add(e),
					() -> savedTags.add(tagRepository.save(t)));
		});

		return savedTags;

	}

	private ArrayList<TagEntity> extractHashtags(String input) {
		ArrayList<TagEntity> hashtags = new ArrayList<>();

		Pattern pattern = Pattern.compile("\\#\\w+"); // Matches '#' followed by word characters

		// Create a Matcher and find hashtags in the input string
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			hashtags.add(new TagEntity(matcher.group().substring(1)));
		}

		return hashtags;
	}

	@Override
	public List<Comment> getPostComments(UUID postId) {
		
		//TODO: to be implemented..
		
		return null;
	}
	
}
