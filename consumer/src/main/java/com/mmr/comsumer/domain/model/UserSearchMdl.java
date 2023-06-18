package com.mmr.comsumer.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class UserSearchMdl {
    private List<UserMdl> userList;
    private int totalPages;
    private int totalElements;
    private int size;
    private int numberOfElements;
    private boolean first;
    private boolean empty;
    private boolean last;
}
