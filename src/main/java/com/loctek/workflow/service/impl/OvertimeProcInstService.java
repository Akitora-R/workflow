package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.activiti.impl.OvertimeInstanceVariable;
import com.loctek.workflow.service.BaseProcessInstanceService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OvertimeProcInstService extends BaseProcessInstanceService<OvertimeInstanceVariable> {
    public OvertimeProcInstService(RuntimeService runtimeService, HistoryService historyService, RepositoryService repositoryService) {
        super(runtimeService, historyService, repositoryService);
    }

    @Override
    protected OvertimeInstanceVariable convertInstanceVariable(Map<String, Object> variables) {
        return new OvertimeInstanceVariable();
    }

    @Override
    public String getDefinitionKey() {
        return "overtime";
    }
}
