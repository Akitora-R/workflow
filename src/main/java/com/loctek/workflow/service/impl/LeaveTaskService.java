package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.dto.impl.LeaveExtraTaskVariables;
import com.loctek.workflow.service.BaseTaskService;
import org.activiti.engine.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class LeaveTaskService extends BaseTaskService<LeaveExtraTaskVariables> {
    public LeaveTaskService(HistoryService historyService) {
        super(historyService);
    }
}
