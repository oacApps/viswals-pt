package com.mmr.publisher.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PublishService {
    void publish(MultipartFile filePath) throws IOException;

}
