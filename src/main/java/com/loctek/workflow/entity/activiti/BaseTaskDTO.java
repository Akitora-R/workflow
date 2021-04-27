package com.loctek.workflow.entity.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseTaskDTO<T extends IBaseExtraTaskVariables> {
    String id;
    String name;
    String assignee;
    LocalDateTime createTime;
    LocalDateTime endTime;
    Boolean finished;
    Boolean approval;
    String comment;
    T extraVariables;
}
