package com.loctek.workflow.entity.activiti.impl;

import com.loctek.workflow.constant.JobGroupList;
import com.loctek.workflow.entity.activiti.BaseInstanceVariable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OvertimeInstanceVariable extends BaseInstanceVariable {
    private String applierGroup;
    private Integer applierLevel;
    private List<String> supervisorCandidateList;
    private List<String> managerCandidateList;
    private List<String> directorCandidateList;
    private List<String> vicePresidentCandidateList;
    private List<String> presidentCandidateList;

    public OvertimeInstanceVariable(String applierId,
                                    String applierDepartmentId,
                                    String applierGroup,
                                    Integer applierLevel,
                                    List<String> supervisorCandidateList,
                                    List<String> managerCandidateList,
                                    List<String> directorCandidateList,
                                    List<String> vicePresidentCandidateList,
                                    List<String> presidentCandidateList) {
        super(applierId, applierDepartmentId);
        this.applierGroup = applierGroup;
        this.applierLevel = applierLevel;
        this.supervisorCandidateList = supervisorCandidateList;
        this.managerCandidateList = managerCandidateList;
        this.directorCandidateList = directorCandidateList;
        this.vicePresidentCandidateList = vicePresidentCandidateList;
        this.presidentCandidateList = presidentCandidateList;
    }

    @Override
    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("applierId", applierId);
            put("applierDepartmentId", applierDepartmentId);
            put("applierGroup", applierGroup);
            put("applierLevel", applierLevel);
            put("group1", JobGroupList.group1);
            put("group2", JobGroupList.group2);
            put("group3", JobGroupList.group3);
            put("group4", JobGroupList.group4);
            put("supervisorCandidateList", supervisorCandidateList);
            put("managerCandidateList", managerCandidateList);
            put("directorCandidateList", directorCandidateList);
            put("vicePresidentCandidateList", vicePresidentCandidateList);
            put("presidentCandidateList", presidentCandidateList);
        }};

    }
}
