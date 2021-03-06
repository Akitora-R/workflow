package com.loctek.workflow.service.impl;

import cn.hutool.core.convert.Convert;
import com.loctek.workflow.entity.activiti.impl.BusinessTripInstanceVariable;
import com.loctek.workflow.service.BaseProcessInstanceService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusinessTripProcInstService extends BaseProcessInstanceService<BusinessTripInstanceVariable> {
    public BusinessTripProcInstService(RuntimeService runtimeService, HistoryService historyService, RepositoryService repositoryService) {
        super(runtimeService, historyService, repositoryService);
    }

    @Override
    protected BusinessTripInstanceVariable convertInstanceVariable(Map<String, Object> variables) {
        return new BusinessTripInstanceVariable(
                (String) variables.get("applierId"),
                (String) variables.get("applierDepartmentId"),
                (String) variables.get("applierGroup"),
                (Integer) variables.get("applierLevel"),
                Convert.toList(String.class, variables.get("supervisorCandidateList")),
                Convert.toList(String.class, variables.get("managerCandidateList")),
                Convert.toList(String.class, variables.get("directorCandidateList")),
                Convert.toList(String.class, variables.get("vicePresidentCandidateList")),
                Convert.toList(String.class, variables.get("presidentCandidateList"))
        );
    }

    @Override
    public String getDefinitionKey() {
        return "businessTrip";
    }
}
