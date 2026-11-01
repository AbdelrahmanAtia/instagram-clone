package com.javaworld.instagram.userinfoservice.restapi;

import com.javaworld.instagram.userinfoservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.userinfoservice.server.api.UsersApi;
import com.javaworld.instagram.userinfoservice.server.dto.CreateUserRequestApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.DeletedUsersResponseApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.FollowUserRequestApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.GenericResponseApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.PartialUpdateUserRequestApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.UnFollowUserRequestApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.UserApiDto;
import com.javaworld.instagram.userinfoservice.service.UserQueryService;
import com.javaworld.instagram.userinfoservice.service.UserService;
import com.javaworld.instagram.userinfoservice.service.criteria.UserCriteria;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dto.UserSearchKeyType;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersApiImpl implements UsersApi {

    private static final Logger logger = LoggerFactory.getLogger(UsersApiImpl.class);

    private final UserService userService;
    private final UserQueryService userQueryService;
    private final UserApiDtoMapper mapper;
    private final ServiceUtil serviceUtil;

    public UsersApiImpl(UserService userService, UserQueryService userQueryService, UserApiDtoMapper mapper,
            ServiceUtil serviceUtil) {
        this.userService = userService;
        this.userQueryService = userQueryService;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public ResponseEntity<UserApiDto> createUser(CreateUserRequestApiDto createUserRequestApiDto) {
        User savedUser = userService.createUser(mapper.mapCreateUserRequestToUserDto(createUserRequestApiDto));
        return ResponseEntity.ok(mapper.toApiDtoWithoutPass(savedUser));
    }

    @Override
    public ResponseEntity<UserApiDto> searchUser(String searchKeyValue, String searchKeyType) {
        UserApiDto userApiDto = mapper.toApiDto(
                userService.searchUser(searchKeyValue, UserSearchKeyType.fromValue(searchKeyType)));
        return ResponseEntity.ok(userApiDto);
    }

    //TODO: this will not appear in swagger doc as i don't know
    // till now how the criteria will be represent in the swagger file
    //TODO: this annotation shall be removed once we defined that api in swagger
    @GetMapping(value = "/users/search")
    public List<UserApiDto> searchForUsers(UserCriteria criteria) {
        List<User> userDtoList = userQueryService.findByCriteria(criteria);
        return mapper.toApiDtoList(userDtoList);
    }

    @Override
    public ResponseEntity<UserApiDto> partialUpdateUser(PartialUpdateUserRequestApiDto partialUpdateUserRequestApiDto) {
        logger.info("partially updating user: " + partialUpdateUserRequestApiDto);
        User userDto = mapper.toDto(partialUpdateUserRequestApiDto);
        return ResponseEntity.ok(mapper.toApiDto(userService.partialUpdateUser(userDto)));
    }

    @Override
    public ResponseEntity<DeletedUsersResponseApiDto> deleteUser(UUID userUuid) {
        int deletedUsersCount = userService.deleteUser(userUuid);
        DeletedUsersResponseApiDto response = new DeletedUsersResponseApiDto().deletedUsersCount(deletedUsersCount)
                .message("users deleted successfully");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GenericResponseApiDto> followUser(FollowUserRequestApiDto followUserRequest) {
        userService.followUser(followUserRequest.getFollowedId());

        return ResponseEntity.ok(getGenericSuccessResponse());
    }

    @Override
    public ResponseEntity<GenericResponseApiDto> unfollowUser(UnFollowUserRequestApiDto unFollowUserRequest) {
        userService.unfollow(unFollowUserRequest.getFollowedId());
        return ResponseEntity.ok(getGenericSuccessResponse());
    }

    @Override
    public ResponseEntity<List<UserApiDto>> getSuggestedUsers(String size) {
        logger.info("retrieving suggested users");
        List<User> suggestedUsers = userService.getSuggestedUsers(size);
        return ResponseEntity.ok(mapper.toApiDtoList(suggestedUsers));
    }

    @Override
    public ResponseEntity<List<UserApiDto>> getUserFollowers(UUID userUuid, Integer page, Integer size) {
        logger.info("retrieving followers of user with uuid {}", userUuid);
        List<User> followers = userService.getUserFollowers(userUuid);
        return ResponseEntity.ok(mapper.toApiDtoList(followers));
    }

    @Override
    public ResponseEntity<List<UserApiDto>> getUserFollowings(UUID userUuid, Integer page,
            Integer size) {
        logger.info("retrieving followings of user with uuid {}", userUuid);
        return ResponseEntity.ok(mapper.toApiDtoList(userService.getUserFollowings(userUuid)));
    }

    @Override
    public ResponseEntity<GenericResponseApiDto> removeFollower(UUID followerUuid, Integer page, Integer size) {
        userService.removeFollower(followerUuid);
        return ResponseEntity.ok(getGenericSuccessResponse());
    }

    private UserApiDto setServiceAddress(UserApiDto userApiDto) {
        userApiDto.setServiceAddress(serviceUtil.getServiceAddress());
        return userApiDto;
    }

    private GenericResponseApiDto getGenericSuccessResponse() {
        GenericResponseApiDto response = new GenericResponseApiDto();
        response.setMessage("operation success");
        return response;
    }

}