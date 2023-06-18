package com.mmr.publisher.domain.mapper;

import com.mmr.publisher.domain.model.UserMdl;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserMdl fromCSVRecord(CSVRecord csvRecord) {

        UserMdl userMdl = new UserMdl();
        userMdl.setId(Long.parseLong(csvRecord.get(0)));
        userMdl.setFirstName(csvRecord.get(1));
        userMdl.setLastName(csvRecord.get(2));
        userMdl.setEmailAddress(csvRecord.get(3));
        userMdl.setCreatedAt(Long.parseLong(csvRecord.get(4)));
        userMdl.setDeletedAt(Long.parseLong(csvRecord.get(5)));
        userMdl.setMergedAt(Long.parseLong(csvRecord.get(6)));
        userMdl.setParentUserId(Long.parseLong(csvRecord.get(7)));
        return userMdl;
    }

}
