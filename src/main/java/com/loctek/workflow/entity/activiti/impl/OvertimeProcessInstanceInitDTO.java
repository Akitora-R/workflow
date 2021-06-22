package com.loctek.workflow.entity.activiti.impl;

import com.loctek.workflow.entity.activiti.BaseProcessInstanceInitDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OvertimeProcessInstanceInitDTO extends BaseProcessInstanceInitDTO {
    @NotBlank
    String applierGroup;
    @NotNull
    Integer applierLevel;
    List<String> supervisorCandidateList;
    List<String> managerCandidateList;
    List<String> directorCandidateList;
    List<String> vicePresidentCandidateList;
    List<String> presidentCandidateList;
    List<String> hrCommissionerCandidateList;
    List<String> hrManagerCandidateList;
}
