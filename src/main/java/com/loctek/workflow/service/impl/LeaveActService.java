package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.activiti.impl.LeaveTaskVariable;
import com.loctek.workflow.service.BaseActivityService;
import com.loctek.workflow.service.BaseTaskService;
import org.activiti.engine.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class LeaveActService extends BaseActivityService<LeaveTaskVariable> {
    public LeaveActService(HistoryService historyService, BaseTaskService<LeaveTaskVariable> taskService) {
        super(historyService, taskService);
    }
}
