package com.mmr.comsumer.domain.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class SearchUtilsTest {

    private SearchUtils searchUtils;

    @BeforeEach
    public void setup() {
        searchUtils = new SearchUtils();
    }

    @Test
    public void testPreparePageableWithDefaultSort() {
        Pagination pagination = new Pagination(0, 10, "firstName:ASC");
        String defaultSortBy = "firstName";
        Sort.Direction defaultSortDirection = Sort.Direction.ASC;

        Pageable pageable = searchUtils.preparePageable(pagination, defaultSortBy, defaultSortDirection);

        Assertions.assertNotNull(pageable);
        Assertions.assertEquals(defaultSortBy, pageable.getSort().getOrderFor(defaultSortBy).getProperty());
        Assertions.assertEquals(defaultSortDirection, pageable.getSort().getOrderFor(defaultSortBy).getDirection());
        Assertions.assertEquals(0, pageable.getPageNumber());
        Assertions.assertEquals(10, pageable.getPageSize());
    }

    @Test
    public void testPreparePageableWithCustomSort() {
        Pagination pagination = new Pagination(0, 10, "lastName:DESC");
        String defaultSortBy = "firstName";
        Sort.Direction defaultSortDirection = Sort.Direction.ASC;

        Pageable pageable = searchUtils.preparePageable(pagination, defaultSortBy, defaultSortDirection);

        Assertions.assertNotNull(pageable);
        Assertions.assertEquals("lastName", pageable.getSort().getOrderFor("lastName").getProperty());
        Assertions.assertEquals(0, pageable.getPageNumber());
        Assertions.assertEquals(10, pageable.getPageSize());
    }

    @Test
    public void testPreparePageableWithInvalidSort() {
        Pagination pagination = new Pagination(0, 10, "invalidSort");
        pagination.setSort("invalidSort");
        String defaultSortBy = "firstName";
        Sort.Direction defaultSortDirection = Sort.Direction.ASC;

        Pageable pageable = searchUtils.preparePageable(pagination, defaultSortBy, defaultSortDirection);

        Assertions.assertNotNull(pageable);
        Assertions.assertEquals(defaultSortBy, pageable.getSort().getOrderFor(defaultSortBy).getProperty());
        Assertions.assertEquals(defaultSortDirection, pageable.getSort().getOrderFor(defaultSortBy).getDirection());
        Assertions.assertEquals(0, pageable.getPageNumber());
        Assertions.assertEquals(10, pageable.getPageSize());
    }

    @Test
    public void testPreparePageableWithException() {
        Pagination pagination = new Pagination();
        String defaultSortBy = "";
        Sort.Direction defaultSortDirection = Sort.Direction.ASC;

        Pageable pageable = searchUtils.preparePageable(pagination, defaultSortBy, defaultSortDirection);

        Assertions.assertNull(pageable);
    }

}
