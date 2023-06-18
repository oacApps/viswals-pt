package com.mmr.comsumer.controller;

import com.mmr.comsumer.domain.model.UserMdl;
import com.mmr.comsumer.domain.model.UserSearchMdl;
import com.mmr.comsumer.domain.utils.Pagination;
import com.mmr.comsumer.domain.utils.SearchUtils;
import com.mmr.comsumer.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private UserController userController;
    private UserService userService;

    private SearchUtils searchUtils;
    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        searchUtils = mock(SearchUtils.class);
        userController = new UserController(searchUtils, userService);
    }

    @Test
    public void testUserById_ReturnsUser() {
        // Arrange
        long userId = 100L;
        UserMdl user = new UserMdl();
        user.setId(100L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmailAddress("johndoe@example.com");
        user.setCreatedAt(1234567890L);
        user.setDeletedAt(1234567890L);
        user.setMergedAt(1234567890L);
        user.setParentUserId(1L);
        when(userService.getUserById(userId)).thenReturn(user);
        ResponseEntity<UserMdl> response = userController.userById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUserById_UserNotFound() {
        long userId = 2L;
        when(userService.getUserById(userId)).thenReturn(null);
        ResponseEntity<UserMdl> response = userController.userById(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testUserById_ExceptionThrown() {
        long userId = 3L;
        when(userService.getUserById(userId)).thenThrow(new RuntimeException("Something went wrong."));
        ResponseEntity<UserMdl> response = userController.userById(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testSearchLogs_ReturnsUserSearchResult() throws Exception {
        Pagination pagination = new Pagination(1, 10, "firstName:ASC");
        String query = "John";
        UserSearchMdl userSearchResult = new UserSearchMdl();
        userSearchResult.setTotalElements(2);

        when(searchUtils.preparePageable(pagination, "firstName", Sort.Direction.ASC))
                .thenReturn(PageRequest.of(0, 10, Sort.by("firstName").ascending()));
        when(userService.searchUser(query, PageRequest.of(0, 10, Sort.by("firstName").ascending())))
                .thenReturn(userSearchResult);

        ResponseEntity<UserSearchMdl> response = userController.searchLogs(pagination, query);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userSearchResult, response.getBody());
    }

    @Test
    public void testSearchLogs_NoResultsFound() throws Exception {
        Pagination pagination = new Pagination(1, 10, "firstName:ASC");
        String query = "Jane";

        when(searchUtils.preparePageable(pagination, "firstName", Sort.Direction.ASC))
                .thenReturn(PageRequest.of(0, 10, Sort.by("firstName").ascending()));
        when(userService.searchUser(query, PageRequest.of(0, 10, Sort.by("firstName").ascending())))
                .thenReturn(new UserSearchMdl());

        ResponseEntity<UserSearchMdl> response = userController.searchLogs(pagination, query);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    public void testSearchLogs_ExceptionThrown() {
        Pagination pagination = new Pagination(1, 10, "firstName:ASC");
        String query = "John";

        when(searchUtils.preparePageable(pagination, "firstName", Sort.Direction.ASC))
                .thenThrow(new RuntimeException("Something went wrong."));

        ResponseEntity<UserSearchMdl> response = userController.searchLogs(pagination, query);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

}
