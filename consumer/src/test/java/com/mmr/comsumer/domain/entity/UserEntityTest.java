package com.mmr.comsumer.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserEntityTest {
    UserEntity user;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setId(100L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmailAddress("johndoe@example.com");
        user.setCreatedAt(1234567890L);
        user.setDeletedAt(1234567890L);
        user.setMergedAt(1234567890L);
        user.setParentUserId(1L);
    }

    @Test
    public void testConstructorAndGetters() {
        long id = 100L;
        String firstName = "John";
        String lastName = "Doe";
        String emailAddress = "johndoe@example.com";
        long createdAt = 1234567890L;
        long deletedAt = 1234567890L;
        long mergedAt = 1234567890L;
        long parentUserId = 1L;


        Assertions.assertEquals(id, user.getId());
        Assertions.assertEquals(firstName, user.getFirstName());
        Assertions.assertEquals(lastName, user.getLastName());
        Assertions.assertEquals(emailAddress, user.getEmailAddress());
        Assertions.assertEquals(createdAt, user.getCreatedAt());
        Assertions.assertEquals(deletedAt, user.getDeletedAt());
        Assertions.assertEquals(mergedAt, user.getMergedAt());
        Assertions.assertEquals(parentUserId, user.getParentUserId());
    }

}
