package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.activiti.impl.LeaveExtraInstanceVariables;
import com.loctek.workflow.service.BaseProcessInstanceService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LeaveProcInstService extends BaseProcessInstanceService<LeaveExtraInstanceVariables> {

    public LeaveProcInstService(RuntimeService runtimeService, HistoryService historyService, RepositoryService repositoryService) {
        super(runtimeService, historyService, repositoryService);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LeaveExtraInstanceVariables convertInstanceVariable(Map<String, Object> variables) {
        return new LeaveExtraInstanceVariables(
                (String) variables.get("applierGroup"),
                (Integer) variables.get("applierLevel"),
                (Double) variables.get("days"),
                (List<String>) variables.get("SupervisorCandidateList"),
                (List<String>) variables.get("ManagerCandidateList"),
                (List<String>) variables.get("DirectorCandidateList")
        );
    }

    @Override
    public String getDefinitionKey() {
        return "leave";
    }
}
