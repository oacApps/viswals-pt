package com.mmr.publisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmr.publisher.domain.mapper.UserMapper;
import com.mmr.publisher.domain.model.UserMdl;
import com.mmr.publisher.utills.FileReaderUtils;
import com.mmr.publisher.utills.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CsvPublishService implements PublishService {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final String routingKey;
    private final String exchangeName;
    private static final int BATCH_SIZE = 1000;
    private FileReaderUtils fileReaderUtils;
    private UserMapper userMapper;

    public CsvPublishService(ObjectMapper objectMapper,
                             RabbitTemplate rabbitTemplate,
                             @Value("${stream.csv.exchange}") String exchangeName,
                             @Value("${stream.csv.routing}") String routingKey,
                             FileReaderUtils fileReaderUtils,
                             UserMapper userMapper) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.fileReaderUtils = fileReaderUtils;
        this.userMapper = userMapper;
    }

    @Override
    public void publish(MultipartFile filePath) throws IOException {
        CSVParser csvParser = fileReaderUtils.readCsvFile(filePath);
        List<UserMdl> batch = new ArrayList<>();
        int lineCount = 0;

        for (CSVRecord csvRecord : csvParser) {
            try {
                UserMdl user = userMapper.fromCSVRecord(csvRecord);
                batch.add(user);
                lineCount++;

                if (lineCount >= BATCH_SIZE) {
                    publishToBroker(batch);
                    batch.clear();
                    lineCount = 0;
                }
            } catch (Exception e) {
                log.error(ResponseMessage.RECORD_PARSE_ERROR.getMessage() + " [" + e.getMessage() + "] " + csvRecord.toString());
            }
        }

        if (!batch.isEmpty()) {
            publishToBroker(batch);
        }

        csvParser.close();
    }

    private void publishToBroker(List<UserMdl> userMdl) throws AmqpException {
        try {
            String jsonBatch = objectMapper.writeValueAsString(userMdl);
            rabbitTemplate.convertAndSend(this.exchangeName, this.routingKey, jsonBatch);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
