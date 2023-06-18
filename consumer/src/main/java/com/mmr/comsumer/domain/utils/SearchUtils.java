package com.mmr.comsumer.domain.utils;

import com.mmr.comsumer.domain.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Slf4j
@Component
public class SearchUtils {
    public Pageable preparePageable(Pagination pagination, String defaultSortBy, Sort.Direction defaultSortDirection) {
        try {
            String sortBy = defaultSortBy;
            String sortDirection = defaultSortDirection.toString();
            if (null != pagination.getSort() && !pagination.getSort().isBlank() && pagination.getSort().contains(":")) {
                String[] sortStr = pagination.getSort().split(":");
                if (isExists(sortStr[0])) {
                    sortBy = sortStr[0];
                }
                sortDirection = sortStr[1].trim();
            }
            Sort sort = Sort.by(sortDirection.length() < 4 ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
            Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getSize(), sort);

            return pageable;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public boolean isExists(String key) {
        Field[] fields = UserEntity.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(key)) {
                return true;
            }
        }
        log.error("Given key {} does not match, falling back to default.", key);
        return false;
    }


}
