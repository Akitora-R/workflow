package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.PagedData;
import com.loctek.workflow.entity.activiti.BaseProcessInstanceDTO;
import com.loctek.workflow.entity.activiti.impl.LeaveInstanceQueryDTO;
import com.loctek.workflow.entity.activiti.impl.LeaveInstanceVariable;
import com.loctek.workflow.service.BaseProcessInstanceService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LeaveProcInstService extends BaseProcessInstanceService<LeaveInstanceVariable> {

    public LeaveProcInstService(RuntimeService runtimeService, HistoryService historyService, RepositoryService repositoryService) {
        super(runtimeService, historyService, repositoryService);
    }

    public PagedData<BaseProcessInstanceDTO<LeaveInstanceVariable>> getInstanceListByQuery(
            Integer pageNo,
            Integer pageSize,
            LeaveInstanceQueryDTO queryDTO) {
        PagedData<BaseProcessInstanceDTO<LeaveInstanceVariable>> pagedData = new PagedData<>();
        HistoricProcessInstanceQuery query = super.historyService.createHistoricProcessInstanceQuery();
        //todo 可能以后会用到
        return pagedData;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected LeaveInstanceVariable convertInstanceVariable(Map<String, Object> variables) {
        return new LeaveInstanceVariable(
                (String) variables.get("applierId"),
                (String) variables.get("applierDepartmentId"),
                (String) variables.get("applierGroup"),
                (Integer) variables.get("applierLevel"),
                (Double) variables.get("days"),
                (List<String>) variables.get("SupervisorCandidateList"),
                (List<String>) variables.get("ManagerCandidateList"),
                (List<String>) variables.get("DirectorCandidateList"),
                (List<String>) variables.get("VicePresidentCandidateList"),
                (List<String>) variables.get("PresidentCandidateList")
        );
    }

    @Override
    public String getDefinitionKey() {
        return "leave";
    }
}
