package com.loctek.workflow.listener.impl;

import com.loctek.workflow.listener.IListener;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("leave")
@Slf4j
public class LeaveListener implements IListener {
    private final LeaveProcInstService leaveProcInstService;

    public LeaveListener(@Lazy LeaveProcInstService leaveProcInstService) {
        this.leaveProcInstService = leaveProcInstService;
    }

    @Override
    public void executeOnProcessComplete(String instanceId) {
        String applierId = leaveProcInstService.getHistoricProcessInstanceByInstanceId(instanceId).getVariable().getApplierId();

        log.info("流程结束, id:{} 申请人id:{}",instanceId,applierId);
    }

    @Override
    public void executeOnTaskComplete(String instanceId) {
        log.info("任务结束, 流程实例id:{}", instanceId);
    }
}
