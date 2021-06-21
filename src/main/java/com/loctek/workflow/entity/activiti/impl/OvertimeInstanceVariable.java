package com.loctek.workflow.entity.activiti.impl;

import com.loctek.workflow.entity.activiti.BaseInstanceVariable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OvertimeInstanceVariable extends BaseInstanceVariable {
    private static final List<String> operationGroup = Collections.singletonList("操作族群");
    private static final List<String> manageGroup = Collections.singletonList("管理族群");
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
            put("operationGroup", operationGroup);
            put("manageGroup", manageGroup);
            put("supervisorCandidateList", supervisorCandidateList);
            put("managerCandidateList", managerCandidateList);
            put("directorCandidateList", directorCandidateList);
            put("vicePresidentCandidateList", vicePresidentCandidateList);
            put("presidentCandidateList", presidentCandidateList);
        }};
    }
}
