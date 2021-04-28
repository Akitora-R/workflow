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
    private final static List<String> group3 = Collections.singletonList("操作");
    private final static List<String> group4 = Collections.singletonList("管理");

    private String applierGroup;
    private Integer applierLevel;
    private Double days;
    private List<String> SupervisorCandidateList;
    private List<String> ManagerCandidateList;
    private List<String> DirectorCandidateList;

    public LeaveInstanceVariable(String applierId, String applierGroup, Integer applierLevel, Double days, List<String> supervisorCandidateList, List<String> managerCandidateList, List<String> directorCandidateList) {
        super(applierId);
        this.applierGroup = applierGroup;
        this.applierLevel = applierLevel;
        this.days = days;
        SupervisorCandidateList = supervisorCandidateList;
        ManagerCandidateList = managerCandidateList;
        DirectorCandidateList = directorCandidateList;
    }

    @Override
    public Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("applierId", LeaveInstanceVariable.super.applierId);
            put("applierGroup", applierGroup);
            put("applierLevel", applierLevel);
            put("days", days);
            put("group1", group1);
            put("group2", group2);
            put("group3", group3);
            put("group4", group4);
            put("SupervisorCandidateList", SupervisorCandidateList);
            put("ManagerCandidateList", ManagerCandidateList);
            put("DirectorCandidateList", DirectorCandidateList);
        }};
    }
}
