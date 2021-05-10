package com.loctek.workflow.entity.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTaskDTO<T extends BaseTaskVariable> {
    String id;
    String businessKey;
    String name;
    String assignee;
    String applierId;
    List<String> candidateList;
    LocalDateTime createTime;
    LocalDateTime endTime;
    Boolean finished;
    Boolean approval;
    String comment;
    T variable;
}
