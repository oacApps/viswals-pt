package com.mmr.publisher.utills;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Slf4j
@Component
public class FileReaderUtils {
    public CSVParser readCsvFile(MultipartFile file) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream());
        CSVParser csvParser = null;
        try {
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            return csvParser;
        } catch (IOException e) {
            log.error(ResponseMessage.ERROR_READING_FILE.getMessage() + e.getMessage());
            throw new IOException(e);
        }
    }
}
