package com.loctek.workflow.listener.impl;

import com.loctek.workflow.listener.IListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("leave")
@Slf4j
public class LeaveListener implements IListener {
    @Override
    public void executeOnProcessComplete(String instanceId) {
        log.info("流程结束, id:{}",instanceId);
    }

    @Override
    public void executeOnTaskComplete(String executionId) {
        log.info("任务结束, 执行id:{}",executionId);
    }
}
