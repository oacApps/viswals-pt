package com.mmr.publisher.utills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileReaderUtilsTest {

    @Mock
    private MultipartFile mockFile;

    @Mock
    private Reader reader;

    @Mock
    InputStream inputStream;

    FileReaderUtils fileReaderUtils;
    @BeforeEach
    public void setUp() {
        fileReaderUtils = new FileReaderUtils();
    }

    @Test
    void testReadCsvFile_NullFile_ThrowsNullPointerException() throws IOException {
        when(mockFile.getInputStream()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> fileReaderUtils.readCsvFile(mockFile));
    }
}
