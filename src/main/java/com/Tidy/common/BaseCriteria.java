package com.Tidy.common;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class BaseCriteria {
    private Long id;
    private int pageNumber = 0;
    private int pageSize = 1000;
    private String sortDirection = Sort.Direction.ASC.name();
    private String orderBy = "id";
}
