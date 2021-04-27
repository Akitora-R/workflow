package com.loctek.workflow.entity.activiti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseProcessInstanceInitDTO{
    @NotBlank
    String businessKey;
    @NotBlank
    String applierId;
}
