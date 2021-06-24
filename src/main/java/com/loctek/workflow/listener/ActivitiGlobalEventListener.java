package com.loctek.workflow.listener;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Component
public class ActivitiGlobalEventListener implements ActivitiEventListener, Serializable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ActivitiGlobalEventListener.class);
    private final RepositoryService repositoryService;
    private final Map<String, ? extends IListener> listenerMap;

    public ActivitiGlobalEventListener(@Lazy RepositoryService repositoryService,
                                       Map<String, ? extends IListener> listenerMap) {
        this.repositoryService = repositoryService;
        this.listenerMap = listenerMap;
    }

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        if (activitiEvent.getType() == ActivitiEventType.PROCESS_COMPLETED) {
            IListener listener = listenerMap.get(getDefKey(activitiEvent.getProcessDefinitionId()));
            if (listener != null) {
                listener.executeOnProcessComplete(activitiEvent.getProcessInstanceId());
            }
        }else if (activitiEvent.getType() == ActivitiEventType.TASK_COMPLETED){
            IListener listener = listenerMap.get(getDefKey(activitiEvent.getProcessDefinitionId()));
            if (listener != null) {
                listener.executeOnTaskComplete(activitiEvent.getExecutionId());
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        return true;
    }

    private String getDefKey(String defId){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(defId).latestVersion().singleResult();
        return processDefinition.getKey();
    }
}
