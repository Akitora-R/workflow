package com.loctek.workflow.entity.dto.impl;

import com.loctek.workflow.entity.dto.IBaseExtraInstanceVariables;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@RequiredArgsConstructor
public class LeaveExtraInstanceVariables implements IBaseExtraInstanceVariables {
    private final static List<String> group1 = Arrays.asList(
            "销售", "市场", "产品研发", "技术", "生产供应链");
    private final static List<String> group2 = Arrays.asList(
            "战略运营", "人力资源", "财务", "风控", "综合");
    private final static List<String> group3 = Collections.singletonList("操作");
    private final static List<String> group4 = Collections.singletonList("管理");

    private final String applierGroup;
    private final Integer applierLevel;
    private final Double days;
    private final List<String> SupervisorCandidateList;
    private final List<String> ManagerCandidateList;
    private final List<String> DirectorCandidateList;

    @Override
    public Map<String, Object> getVariables() {
        return new HashMap<String, Object>() {{
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

    @Override
    public List<String> getVariableFields() {
        return Arrays.asList("applierGroup", "applierLevel", "days");
    }
}
