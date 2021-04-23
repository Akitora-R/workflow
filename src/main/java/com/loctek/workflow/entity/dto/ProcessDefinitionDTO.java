package com.loctek.workflow.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionDTO {
    private String id;
    private String name;
    private String key;
    private Integer version;
    private Boolean isSuspended;
}
