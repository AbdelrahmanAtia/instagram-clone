
package com.javaworld.instagram.userinfoservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.javaworld.instagram.userinfoservice.integration.PostServiceIntegration;
import com.javaworld.instagram.userinfoservice.persistence.FollowerRepository;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapperImpl;

@ExtendWith({ MockitoExtension.class, SpringExtension.class })
@ActiveProfiles("test")
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private FollowerRepository followerRepository;

	@Mock
	private UserMapperImpl userMapper;

	@Mock
	private PostServiceIntegration postServiceIntegration;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;
	
	private UserEntity userEntity;

	@BeforeEach
	public void setUp() {

		user = new User();
		user.setUsername("testUser");

		userEntity = new UserEntity();
		userEntity.setUsername("testUser");

		when(userMapper.toEntity(user)).thenReturn(userEntity);
		when(userMapper.toDto(userEntity)).thenReturn(user);

	}

	@Test
    public void testCreateUser_Success() {
        when(userRepository.save(userEntity)).thenReturn(userEntity);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        verify(userRepository, times(1)).save(userEntity);
   
        verify(userMapper, times(1)).toDto(userEntity);     verify(userMapper, times(1)).toEntity(user);
    }


}
