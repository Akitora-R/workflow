package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.activiti.impl.OvertimeTaskVariable;
import com.loctek.workflow.service.BaseTaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Service;

@Service
public class OvertimeTaskService extends BaseTaskService<OvertimeTaskVariable> {
    public OvertimeTaskService(HistoryService historyService, TaskService taskService) {
        super(historyService, taskService);
    }

    @Override
    public String getDefinitionKey() {
        return "overtime";
    }
}
