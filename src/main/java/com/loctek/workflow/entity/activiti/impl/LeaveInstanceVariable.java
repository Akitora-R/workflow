package com.loctek.workflow.entity.activiti.impl;

import com.loctek.workflow.entity.activiti.BaseInstanceVariable;
import lombok.*;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class LeaveInstanceVariable extends BaseInstanceVariable {
    private final static List<String> group1 = Arrays.asList(
            "销售", "市场", "产品研发", "技术", "生产供应链");
    private final static List<String> group2 = Arrays.asList(
            "战略运营", "人力资源", "财务", "风控", "综合");
    private final static List<String> group3 = Collections.singletonList(
            "操作");
    private final static List<String> group4 = Collections.singletonList(
            "管理");

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
