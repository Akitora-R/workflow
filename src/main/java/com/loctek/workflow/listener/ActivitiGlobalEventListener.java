package com.loctek.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@Component
public class ActivitiGlobalEventListener implements ActivitiEventListener, Serializable {
//    public void notify(DelegateTask delegateTask) {
//        String name = delegateTask.getName();
//        String assignee = delegateTask.getAssignee();
//        Set<IdentityLink> candidates = delegateTask.getCandidates();
//        Map<String, Object> variables = delegateTask.getVariables();
//        log.info("任务名：{}",name);
//        log.info("分配人：{}",assignee);
//        log.info("候选人：{}",candidates);
//        log.info("变量：{}",variables);
//    }

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType eventType = activitiEvent.getType();
//        String evenName = eventType.name();
        log.info("事件名：{}",eventType);

    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
