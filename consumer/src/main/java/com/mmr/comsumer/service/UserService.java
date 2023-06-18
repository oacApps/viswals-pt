package com.mmr.comsumer.service;

import com.mmr.comsumer.domain.entity.UserEntity;
import com.mmr.comsumer.domain.mapper.UserMapper;
import com.mmr.comsumer.domain.model.UserMdl;
import com.mmr.comsumer.domain.model.UserSearchMdl;
import com.mmr.comsumer.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@EnableScheduling
public class UserService {


    private RedisService redisService;
    private UserRepository userRepository;

    public UserService(RedisService redisService, UserRepository userRepository) {
        this.redisService = redisService;
        this.userRepository = userRepository;
    }

    public UserMdl getUserById(long id) {
        UserMdl user = null;
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            user = UserMapper.INSTANCE.toMdl(userEntity.get());
        }
        return user;
    }

    public UserSearchMdl searchUser(String userSearchParams, Pageable pageable) throws Exception {
        UserSearchMdl UserSearchMdl = null;
        try {
            Page<UserEntity> userEntities = userRepository.searchByFirstNameOrLastNameOrEmailAddressContaining(userSearchParams, pageable);
            UserSearchMdl = UserMapper.INSTANCE.toUserSearchMdl(userEntities);
        } catch (ParseException parseException) {
            parseException.getStackTrace();
        }
        return UserSearchMdl;
    }

    public void saveFromCache() {
        List<UserMdl> userMdlList = (List<UserMdl>) redisService.get("csvUserData");
        if (userMdlList != null) {
            List<UserEntity> userEntities = UserMapper.INSTANCE.toEntityList(userMdlList);
            userRepository.saveAll(userEntities);
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void processUserMessages() {
        saveFromCache();
    }
}
