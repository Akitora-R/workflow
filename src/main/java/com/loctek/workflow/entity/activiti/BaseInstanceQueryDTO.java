package com.loctek.workflow.entity.activiti;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseInstanceQueryDTO {
    String businessKey;
    String applierId;
    String applierDepartmentId;
    LocalDateTime dateTimeFrom;
    LocalDateTime dateTimeTo;
}
