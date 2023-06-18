package com.mmr.publisher.controller;

import com.mmr.publisher.service.CsvPublishService;
import com.mmr.publisher.utills.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilePublishControllerTest {
    @Mock
    private CsvPublishService publishService;

    @InjectMocks
    FilePublishController filePublishController;

    @Test
    void testPublishCsvFileWithValidFile() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.csv", "text/csv", "file content".getBytes());
        doNothing().when(publishService).publish(mockFile);
        ResponseEntity<String> response = filePublishController.publishCsvFile(mockFile);
        verify(publishService, times(1)).publish(mockFile);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseMessage.CSV_PUBLISH_SUCCESS.getMessage(), response.getBody());
    }

    @Test
    void testPublishCsvFileWithInvalidFile() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", new byte[0]);
        ResponseEntity<String> response = filePublishController.publishCsvFile(mockFile);
        verify(publishService, never()).publish(any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseMessage.INVALID_FILE.getMessage(), response.getBody());
    }

    @Test
    void testPublishCsvFileWithException() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.csv", "text/csv", "file content".getBytes());
        IOException expectedException = new IOException("Some error message");
        doThrow(expectedException).when(publishService).publish(mockFile);
        ResponseEntity<String> response = filePublishController.publishCsvFile(mockFile);
        verify(publishService, times(1)).publish(mockFile);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ResponseMessage.CSV_PUBLISH_FAILED.getMessage() + expectedException.getMessage(), response.getBody());
    }
}
