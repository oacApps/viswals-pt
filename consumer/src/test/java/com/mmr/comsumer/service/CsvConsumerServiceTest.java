package com.mmr.comsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmr.comsumer.domain.model.UserMdl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CsvConsumerServiceTest {

    private CsvConsumerService csvConsumerService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private RedisService redisService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        csvConsumerService = new CsvConsumerService(objectMapper, userService, redisService);
    }

    @Test
    public void consume_ValidMessage_SuccessfullySetsDataInRedis() throws JsonProcessingException {
        String message = "validMessage";
        UserMdl[] userMdls = {new UserMdl()};
        List<UserMdl> expectedUserMdlList = Arrays.asList(userMdls);
        Mockito.when(objectMapper.readValue(message, UserMdl[].class)).thenReturn(userMdls);
        csvConsumerService.consume(message);
        Mockito.verify(redisService).set("csvUserData", expectedUserMdlList);
    }

    @Test
    public void consume_InvalidMessage_ThrowsRuntimeException() throws JsonProcessingException {
        String message = "invalidMessage";
        Mockito.when(objectMapper.readValue(message, UserMdl[].class)).thenThrow(new JsonProcessingException("Invalid JSON") {});
        assertThrows(RuntimeException.class, () -> csvConsumerService.consume(message));
    }
}
