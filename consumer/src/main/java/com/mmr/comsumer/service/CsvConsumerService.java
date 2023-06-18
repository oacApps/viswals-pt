package com.mmr.comsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmr.comsumer.domain.model.UserMdl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CsvConsumerService implements ConsumerService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private RedisService redisService;

    public CsvConsumerService(ObjectMapper objectMapper, UserService userService, RedisService redisService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    @RabbitListener(queues = "${stream.csv.queue}")
    public void consume(String message) {
        try {
            List<UserMdl> userMdlList = Arrays.asList(objectMapper.readValue(message, UserMdl[].class));
            redisService.set("csvUserData", userMdlList);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
