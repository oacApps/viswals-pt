package com.mmr.comsumer.service;

import com.mmr.comsumer.domain.entity.UserEntity;
import com.mmr.comsumer.domain.mapper.UserMapper;
import com.mmr.comsumer.domain.model.UserMdl;
import com.mmr.comsumer.domain.model.UserSearchMdl;
import com.mmr.comsumer.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    RedisService redisService;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(redisService, userRepository);
    }

    @Test
    public void testGetUserById_WhenUserExists() {
        long id = 1L;
        UserEntity userEntity = new UserEntity();
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        UserMdl result = userService.getUserById(id);
        assertNotNull(result);
        Assertions.assertEquals(userEntity.getId(), result.getId());
        Assertions.assertEquals(userEntity.getFirstName(), result.getFirstName());
    }
    @Test
    public void testGetUserById_WhenUserDoesNotExist() {
        long id = 2L;
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());
        UserMdl result = userService.getUserById(id);
        assertNull(result);
    }

    @Test
    void searchUser_ReturnsUserSearchMdl_WhenUserEntitiesExist() throws Exception {
        Page<UserEntity> userEntities = mock(Page.class);
        when(userRepository.searchByFirstNameOrLastNameOrEmailAddressContaining(anyString(), any(Pageable.class)))
                .thenReturn(userEntities);
        String userSearchParams = "John";
        Pageable pageable = mock(Pageable.class);
        UserSearchMdl result = userService.searchUser(userSearchParams, pageable);
        assertNotNull(result);
        verify(userRepository).searchByFirstNameOrLastNameOrEmailAddressContaining(userSearchParams, pageable);
    }

    @Test
    void searchUser_ReturnsNull_WhenParseExceptionOccurs() throws Exception {
        when(userRepository.searchByFirstNameOrLastNameOrEmailAddressContaining(anyString(), any(Pageable.class)))
                .thenThrow(ParseException.class);
        String userSearchParams = "John";
        Pageable pageable = mock(Pageable.class);
        UserSearchMdl result = userService.searchUser(userSearchParams, pageable);
        assertNull(result);
        verify(userRepository).searchByFirstNameOrLastNameOrEmailAddressContaining(userSearchParams, pageable);
    }

}
