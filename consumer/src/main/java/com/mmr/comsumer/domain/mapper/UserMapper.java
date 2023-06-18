package com.mmr.comsumer.domain.mapper;

import com.mmr.comsumer.domain.entity.UserEntity;
import com.mmr.comsumer.domain.model.UserMdl;
import com.mmr.comsumer.domain.model.UserSearchMdl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.expression.ParseException;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserMdl userMdl) throws ParseException;

    UserMdl toMdl(UserEntity userEntity) throws ParseException;

    List<UserEntity> toEntityList(List<UserMdl> userMdl) throws ParseException;

    List<UserMdl> toMdlList(List<UserEntity> userEntity) throws ParseException;

    @Mapping(source = "content", target = "userList")
    UserSearchMdl toUserSearchMdl(Page<UserEntity> page) throws ParseException;


}
