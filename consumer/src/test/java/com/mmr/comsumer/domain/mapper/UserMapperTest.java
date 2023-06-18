package com.mmr.comsumer.domain.mapper;

import com.mmr.comsumer.domain.entity.UserEntity;
import com.mmr.comsumer.domain.model.UserMdl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Test
    void toEntity() {
        UserMdl userMdl = new UserMdl();
        userMdl.setId(100L);
        userMdl.setFirstName("John");
        userMdl.setLastName("Doe");
        userMdl.setEmailAddress("johndoe@example.com");
        userMdl.setCreatedAt(1234567890L);
        userMdl.setDeletedAt(1234567890L);
        userMdl.setMergedAt(1234567890L);
        userMdl.setParentUserId(1L);

        UserEntity userEntity = UserMapper.INSTANCE.toEntity(userMdl);

        Assertions.assertEquals(userMdl.getId(), userEntity.getId());
        Assertions.assertEquals(userMdl.getFirstName(), userEntity.getFirstName());
        Assertions.assertEquals(userMdl.getLastName(), userEntity.getLastName());
        Assertions.assertEquals(userMdl.getEmailAddress(), userEntity.getEmailAddress());
        Assertions.assertEquals(userMdl.getCreatedAt(), userEntity.getCreatedAt());
        Assertions.assertEquals(userMdl.getDeletedAt(), userEntity.getDeletedAt());
        Assertions.assertEquals(userMdl.getMergedAt(), userEntity.getMergedAt());
        Assertions.assertEquals(userMdl.getParentUserId(), userEntity.getParentUserId());

    }

    @Test
    void toMdl() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(100L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmailAddress("johndoe@example.com");
        userEntity.setCreatedAt(1234567890L);
        userEntity.setDeletedAt(1234567890L);
        userEntity.setMergedAt(1234567890L);
        userEntity.setParentUserId(1L);

        UserMdl userMdl = UserMapper.INSTANCE.toMdl(userEntity);

        Assertions.assertEquals(userEntity.getId(), userMdl.getId());
        Assertions.assertEquals(userEntity.getFirstName(), userMdl.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), userMdl.getLastName());
        Assertions.assertEquals(userEntity.getEmailAddress(), userMdl.getEmailAddress());
        Assertions.assertEquals(userEntity.getCreatedAt(), userMdl.getCreatedAt());
        Assertions.assertEquals(userEntity.getDeletedAt(), userMdl.getDeletedAt());
        Assertions.assertEquals(userEntity.getMergedAt(), userMdl.getMergedAt());
        Assertions.assertEquals(userEntity.getParentUserId(), userMdl.getParentUserId());
    }
}
