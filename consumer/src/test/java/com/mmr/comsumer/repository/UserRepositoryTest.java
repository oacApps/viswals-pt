package com.mmr.comsumer.repository;

import com.mmr.comsumer.domain.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void searchByFirstNameOrLastNameOrEmailAddressContaining_WithSearchTerm_ReturnsMatchingUsers() {
        String searchTerm = "John";
        Page<UserEntity> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(userRepository.searchByFirstNameOrLastNameOrEmailAddressContaining(anyString(), any(Pageable.class)))
                .thenReturn(expectedPage);
        Page<UserEntity> resultPage = userRepository.searchByFirstNameOrLastNameOrEmailAddressContaining(searchTerm, pageable);

        verify(userRepository, times(1)).searchByFirstNameOrLastNameOrEmailAddressContaining(searchTerm, pageable);
        verifyNoMoreInteractions(userRepository);
        assertEquals(expectedPage, resultPage);
    }

    @Test
    public void searchByFirstNameOrLastNameOrEmailAddressContaining_WithEmptySearchTerm_ReturnsAllUsers() {
        String searchTerm = "";
        Page<UserEntity> expectedPage = mock(Page.class);
        Pageable pageable = mock(Pageable.class);

        when(userRepository.searchByFirstNameOrLastNameOrEmailAddressContaining(anyString(), any(Pageable.class)))
                .thenReturn(expectedPage);
        Page<UserEntity> resultPage = userRepository.searchByFirstNameOrLastNameOrEmailAddressContaining(searchTerm, pageable);

        verify(userRepository, times(1)).searchByFirstNameOrLastNameOrEmailAddressContaining(searchTerm, pageable);
        verifyNoMoreInteractions(userRepository);
        assertEquals(expectedPage, resultPage);
    }
}
