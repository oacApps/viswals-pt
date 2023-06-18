package com.mmr.comsumer.service;

import com.mmr.comsumer.domain.model.UserMdl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplateMock;
    @Mock
    private ListOperations<String, Object> listOperationsMock;
    private RedisService redisService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplateMock.opsForList()).thenReturn(listOperationsMock);
        redisService = new RedisService(redisTemplateMock);
    }

    @Test
    public void testSet_SuccessfulPush() {
        String key = "testKey";
        List<UserMdl> userMdlList = new ArrayList<>();
        UserMdl userMdl = new UserMdl();
        userMdl.setId(100L);
        userMdl.setFirstName("John");
        userMdl.setLastName("Doe");
        userMdl.setEmailAddress("johndoe@example.com");
        userMdl.setCreatedAt(1234567890L);
        userMdl.setDeletedAt(1234567890L);
        userMdl.setMergedAt(1234567890L);
        userMdl.setParentUserId(1L);

        userMdlList.add(userMdl);

        redisService.set(key, userMdlList);
        verify(listOperationsMock).rightPush(key, userMdlList);
    }

    @Test
    public void testSet_ExceptionThrown() {
        // Arrange
        String key = "testKey";
        List<UserMdl> userMdlList = new ArrayList<>();

        UserMdl userMdl = new UserMdl();
        userMdl.setId(100L);
        userMdl.setFirstName("John");
        userMdl.setLastName("Doe");
        userMdl.setEmailAddress("johndoe@example.com");
        userMdl.setCreatedAt(1234567890L);
        userMdl.setDeletedAt(1234567890L);
        userMdl.setMergedAt(1234567890L);
        userMdl.setParentUserId(1L);

        userMdlList.add(userMdl);

        doThrow(new RuntimeException("Redis error")).when(listOperationsMock).rightPush(key, userMdlList);
        redisService.set(key, userMdlList);
        verify(listOperationsMock).rightPush(key, userMdlList);
    }

    @Test
    public void testSet_EmptyList() {
        String key = "testKey";
        List<UserMdl> userMdlList = new ArrayList<>();
        redisService.set(key, userMdlList);
        verify(listOperationsMock).rightPush(key, userMdlList);
    }

    @Test
    void shouldReturnElementWhenAvailable() {
        String key = "testKey";
        Object expected = new Object();
        when(listOperationsMock.leftPop(key)).thenReturn(expected);
        Object result = redisService.get(key);
        assertEquals(expected, result);
        verify(listOperationsMock).leftPop(key);
        verifyNoMoreInteractions(listOperationsMock);
    }

    @Test
    void shouldReturnNullWhenExceptionOccurs() {
        String key = "testKey";
        when(listOperationsMock.leftPop(key)).thenThrow(new RuntimeException());
        Object result = redisService.get(key);
        assertNull(result);
        verify(listOperationsMock).leftPop(key);
        verifyNoMoreInteractions(listOperationsMock);
    }
}
