package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.activiti.impl.BusinessTripTaskVariable;
import com.loctek.workflow.service.BaseTaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Service;

@Service
public class BusinessTripTaskService extends BaseTaskService<BusinessTripTaskVariable> {
    public BusinessTripTaskService(HistoryService historyService, TaskService taskService) {
        super(historyService, taskService);
    }

    @Override
    public String getDefinitionKey() {
        return "businessTrip";
    }
}
