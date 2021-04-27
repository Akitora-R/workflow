package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.dto.impl.LeaveExtraTaskVariables;
import com.loctek.workflow.service.BaseActivityService;
import com.loctek.workflow.service.BaseTaskService;
import org.activiti.engine.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class LeaveActService extends BaseActivityService<LeaveExtraTaskVariables> {
    public LeaveActService(HistoryService historyService, BaseTaskService<LeaveExtraTaskVariables> taskService) {
        super(historyService, taskService);
    }
}
