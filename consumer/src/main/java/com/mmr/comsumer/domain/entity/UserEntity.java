package com.mmr.comsumer.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users_data")
public class UserEntity {
    @Id
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "deleted_at")
    private long deletedAt;

    @Column(name = "merged_at")
    private long mergedAt;

    @Column(name = "parent_user_id")
    private long parentUserId;
}
