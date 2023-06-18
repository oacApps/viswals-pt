package com.mmr.comsumer.repository;

import com.mmr.comsumer.domain.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.firstName LIKE %:searchTerm% OR u.lastName LIKE %:searchTerm% OR u.emailAddress LIKE %:searchTerm%")
    Page<UserEntity> searchByFirstNameOrLastNameOrEmailAddressContaining(String searchTerm, Pageable pageable);
}
