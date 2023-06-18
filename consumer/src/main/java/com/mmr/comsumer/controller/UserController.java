package com.mmr.comsumer.controller;

import com.mmr.comsumer.domain.model.UserMdl;
import com.mmr.comsumer.domain.model.UserSearchMdl;
import com.mmr.comsumer.domain.utils.Pagination;
import com.mmr.comsumer.domain.utils.SearchUtils;
import com.mmr.comsumer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private SearchUtils searchUtils;

    private UserService userService;

    public UserController(SearchUtils searchUtils, UserService userService) {
        this.searchUtils = searchUtils;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserMdl> userById(@PathVariable("id") long id) {
        try {
            UserMdl user = userService.getUserById(id);
            if (null == user) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<UserSearchMdl> searchLogs(@ModelAttribute Pagination pagination, @RequestParam(value = "query") String query) {

        UserSearchMdl userSearchResult = null;

        try {
            userSearchResult = userService.searchUser(query, searchUtils.preparePageable(pagination, "firstName", Sort.Direction.ASC));
            if (userSearchResult.getTotalElements() <= 0) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(userSearchResult);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
