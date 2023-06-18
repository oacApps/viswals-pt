package com.mmr.publisher.domain.mapper;

import com.mmr.publisher.domain.model.UserMdl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper();
    }
    @Test
    public void testFromCSVRecord() throws IOException {
        // Create a sample CSVRecord for testing
        String csvString = "1,John,Doe,johndoe@example.com,1623896400,0,0,0";
        CSVParser csvParser = CSVParser.parse(csvString, CSVFormat.DEFAULT);
        CSVRecord csvRecord = csvParser.getRecords().get(0);

        // Call the method under test
        UserMdl userMdl = userMapper.fromCSVRecord(csvRecord);

        // Assert the expected values
        Assertions.assertEquals(1, userMdl.getId());
        Assertions.assertEquals("John", userMdl.getFirstName());
        Assertions.assertEquals("Doe", userMdl.getLastName());
        Assertions.assertEquals("johndoe@example.com", userMdl.getEmailAddress());
        Assertions.assertEquals(1623896400, userMdl.getCreatedAt());
        Assertions.assertEquals(0, userMdl.getDeletedAt());
        Assertions.assertEquals(0, userMdl.getMergedAt());
        Assertions.assertEquals(0, userMdl.getParentUserId());
    }
}
