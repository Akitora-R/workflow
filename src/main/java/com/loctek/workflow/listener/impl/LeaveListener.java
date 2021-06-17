package com.loctek.workflow.listener.impl;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loctek.workflow.entity.activiti.BaseTaskDTO;
import com.loctek.workflow.entity.activiti.impl.LeaveTaskVariable;
import com.loctek.workflow.listener.IListener;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import com.loctek.workflow.service.impl.LeaveTaskService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("leave")
@Slf4j
public class LeaveListener implements IListener {
    private final LeaveProcInstService leaveProcInstService;
    private final LeaveTaskService leaveTaskService;
    private final TaskService taskService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public LeaveListener(@Lazy LeaveProcInstService leaveProcInstService, @Lazy LeaveTaskService leaveTaskService,@Lazy TaskService taskService, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.leaveProcInstService = leaveProcInstService;
        this.leaveTaskService = leaveTaskService;
        this.taskService = taskService;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void executeOnProcessComplete(String instanceId) {
        String businessKey = leaveProcInstService.getHistoricProcessInstanceByInstanceId(instanceId).getBusinessKey();
        BaseTaskDTO<LeaveTaskVariable> lastTask = leaveTaskService.getLastTaskByBusinessKey(businessKey);
        if (lastTask == null) {
            log.warn("当前流程没有任务！");
            return;
        }
        Boolean approval = taskService.getVariableLocal(lastTask.getId(), "approval", Boolean.class);
        if (approval) {
            log.info("执行完成且通过，调用接口写入，bk：{}", businessKey);
            String body = restTemplate.getForEntity("http://192.168.0.239:18888/salary/api/leaveApi.do?method=applyLeaveToFeiShu&id=" + businessKey, String.class).getBody();
            try {
                Assert.isTrue(objectMapper.readTree(body).get("code").asInt(500) == 200);
            } catch (JsonProcessingException e) {
                log.error("调用出错了嗯...", e);
            }
        }
    }

    @Override
    public void executeOnTaskComplete(String instanceId) {
        log.info("任务结束, 流程实例id:{}", instanceId);
    }
}
