package com.loctek.workflow.entity.activiti;

import com.loctek.workflow.constant.AuditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditStatusDTO {
    String businessKey;
    String lastTaskId;
    AuditStatus auditStatus;
    String description;
}
