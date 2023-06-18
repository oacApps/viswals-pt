package com.mmr.publisher.controller;

import com.mmr.publisher.service.CsvPublishService;
import com.mmr.publisher.service.PublishService;
import com.mmr.publisher.utills.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/v1/publish")
public class FilePublishController {

    private final PublishService publishService;

    @Autowired
    public FilePublishController(CsvPublishService readerService) {
        this.publishService = readerService;
    }
    @PostMapping("/csv")
    public ResponseEntity<String> publishCsvFile(@RequestParam("file") MultipartFile filePath) {
        if (filePath == null || filePath.isEmpty()) {
            log.debug(ResponseMessage.INVALID_FILE.getMessage());
            return ResponseEntity.badRequest().body(ResponseMessage.INVALID_FILE.getMessage());
        }
        try {
            publishService.publish(filePath);
            return ResponseEntity.ok(ResponseMessage.CSV_PUBLISH_SUCCESS.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage.CSV_PUBLISH_FAILED.getMessage() + e.getMessage());
        }
    }
}
