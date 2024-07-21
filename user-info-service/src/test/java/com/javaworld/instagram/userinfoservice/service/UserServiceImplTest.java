/*
package com.javaworld.instagram.userinfoservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaworld.instagram.userinfoservice.integration.PostServiceIntegration;
import com.javaworld.instagram.userinfoservice.persistence.FollowerRepository;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapper;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapperImpl;

import liquibase.integration.spring.SpringLiquibase;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FollowerRepository followerRepository;

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Mock
    private PostServiceIntegration postServiceIntegration;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserEntity userEntity;
    
    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void setUp() {
    	
    	System.out.println(">> starting setUp().................");
        user = new User();
        user.setUsername("testUser");

        userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        //when(userMapper.toEntity(user)).thenReturn(userEntity);
        //when(userMapper.toDto(userEntity)).thenReturn(user);
        
        // Print all bean names
        printAllBeanNames();
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

    
    @Test
    public void testCreateUser_DuplicateKeyException() {
        when(userRepository.save(userEntity)).thenThrow(new DuplicateKeyException("Duplicate key"));

        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("Duplicate key, UserName: testUser", exception.getMessage());
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toEntity(user);
    }
    
    
    private void printAllBeanNames() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("========== Beans in ApplicationContext ===================");
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        System.out.println("===========================================================");
    }    
    
    @TestConfiguration
    static class MyServiceTestConfiguration {
    	
        @Bean(name = "userMapper")
        public UserMapper userMapper() {
        	System.out.println(">>>>>>>>>>>>>");
        	
        	UserMapperImpl userMapperImpl = new UserMapperImpl();
        	
        	System.out.println("========= userMapperImpl ============");
        	System.out.println(userMapperImpl);
        	System.out.println("=====================================");
            return userMapperImpl;
        }
        
        
        
        @Bean
        @Primary
        public SpringLiquibase liquibase() {
            return new SpringLiquibase() {
                @Override
                public void afterPropertiesSet() {
                    // Do nothing
                }
            };
        } 
           
        
        
    }
    
}
*/
