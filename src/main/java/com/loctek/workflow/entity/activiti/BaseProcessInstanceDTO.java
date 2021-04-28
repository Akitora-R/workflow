package com.loctek.workflow.entity.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProcessInstanceDTO<V extends BaseInstanceVariable> {
    String id;
    String name;
    String definitionKey;
    String definitionName;
    String businessKey;
    LocalDateTime startTime;
    LocalDateTime endTime;
    V variable;
}
