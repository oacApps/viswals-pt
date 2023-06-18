package com.mmr.publisher.domain.model;

import lombok.Data;

@Data
public class UserMdl {
    private long id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private long createdAt;
    private long deletedAt;
    private long mergedAt;
    private long parentUserId;
}
