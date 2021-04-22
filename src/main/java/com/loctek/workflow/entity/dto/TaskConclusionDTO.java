package com.loctek.workflow.entity.dto;

import lombok.Data;

@Data
public class TaskConclusionDTO {
    private String taskId;
    private Boolean approval;
    private String userId;
    private String comment;
}
