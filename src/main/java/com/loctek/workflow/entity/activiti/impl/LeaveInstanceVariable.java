package com.loctek.workflow.entity.activiti.impl;

import com.loctek.workflow.entity.activiti.BaseInstanceVariable;
import lombok.*;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class LeaveInstanceVariable extends BaseInstanceVariable {
    private final static List<String> group1 = Arrays.asList("销售族群", "市场族群", "产品研发族群", "技术族群", "生产供应链族群");
    private final static List<String> group2 = Arrays.asList("战略运营族群", "人力资源族群", "财务族群", "风控族群", "综合族群");
    private final static List<String> group3 = Collections.singletonList("操作族群");
    private final static List<String> group4 = Collections.singletonList("管理族群");

    private String applierGroup;
    private Integer applierLevel;
    private Double days;
    private List<String> supervisorCandidateList;
    private List<String> managerCandidateList;
    private List<String> directorCandidateList;
    private List<String> vicePresidentCandidateList;
    private List<String> presidentCandidateList;

    public LeaveInstanceVariable(String applierId,
                                 String applierDepartmentId,
                                 String applierGroup,
                                 Integer applierLevel,
                                 Double days,
                                 List<String> supervisorCandidateList,
                                 List<String> managerCandidateList,
                                 List<String> directorCandidateList,
                                 List<String> vicePresidentCandidateList,
                                 List<String> presidentCandidateList) {
        super(applierId,applierDepartmentId);
        this.applierGroup = applierGroup;
        this.applierLevel = applierLevel;
        this.days = days;
        this.supervisorCandidateList = supervisorCandidateList;
        this.managerCandidateList = managerCandidateList;
        this.directorCandidateList = directorCandidateList;
        this.vicePresidentCandidateList = vicePresidentCandidateList;
        this.presidentCandidateList = presidentCandidateList;
    }

    @Override
    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("applierId", LeaveInstanceVariable.super.applierId);
            put("applierDepartmentId", LeaveInstanceVariable.super.applierDepartmentId);
            put("applierGroup", applierGroup);
            put("applierLevel", applierLevel);
            put("days", days);
            put("group1", group1);
            put("group2", group2);
            put("group3", group3);
            put("group4", group4);
            put("supervisorCandidateList", supervisorCandidateList);
            put("managerCandidateList", managerCandidateList);
            put("directorCandidateList", directorCandidateList);
            put("vicePresidentCandidateList", vicePresidentCandidateList);
            put("presidentCandidateList", presidentCandidateList);
        }};
    }
}
