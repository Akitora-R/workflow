package com.loctek.workflow.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedData<T> {
    Integer pageNo;
    Integer pageSize;
    Integer count;
    Long totalCount;
    Boolean hasMore;
    List<T> data;
}
