package com.loctek.workflow.entity.activiti;

import com.loctek.workflow.constant.AuditStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditStatusDTO {
    String businessKey;
    String lastTaskId;
    String taskName;
    AuditStatus auditStatus;
    List<String> auditorList;
    String assignee;
}
