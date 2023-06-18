package com.mmr.publisher.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmr.publisher.domain.mapper.UserMapper;
import com.mmr.publisher.domain.model.UserMdl;
import com.mmr.publisher.utills.FileReaderUtils;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvPublishServiceTest {

    @Mock
    private CSVParser csvParser;

    @Mock
    RabbitTemplate rabbitTemplate;

    @Mock
    private UserMapper userMapper;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    FileReaderUtils fileReaderUtils;

    private CsvPublishService csvPublishService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        csvPublishService = new CsvPublishService(objectMapper, rabbitTemplate, "exchangeName", "routingKey", fileReaderUtils, userMapper);
    }

    @Test
    public void publish_EmptyFile_NoPublishing() throws IOException {
        MultipartFile emptyFile = mock(MultipartFile.class);
        when(csvParser.iterator()).thenReturn(new ArrayList<CSVRecord>().iterator());
        when(fileReaderUtils.readCsvFile(emptyFile)).thenReturn(csvParser);
        csvPublishService.publish(emptyFile);
        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), anyString());
    }

    @Test
    public void testPublish() throws IOException {
        // Mock dependencies
        MockMultipartFile mockFile = new MockMultipartFile("file.csv", new byte[0]);
        List<CSVRecord> mockRecords = new ArrayList<>();
        CSVRecord mockRecord = mock(CSVRecord.class);
        mockRecords.add(mockRecord);

        when(fileReaderUtils.readCsvFile(mockFile)).thenReturn(csvParser);
        when(csvParser.iterator()).thenReturn(mockRecords.iterator());
        when(userMapper.fromCSVRecord(mockRecord)).thenReturn(new UserMdl());

        csvPublishService.publish(mockFile);

        verify(fileReaderUtils).readCsvFile(mockFile);
        verify(userMapper).fromCSVRecord(mockRecord);
        verify(csvParser).close();
    }
}
