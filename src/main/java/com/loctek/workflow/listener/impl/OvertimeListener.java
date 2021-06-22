package com.loctek.workflow.listener.impl;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loctek.workflow.entity.activiti.BaseProcessInstanceDTO;
import com.loctek.workflow.entity.activiti.BaseTaskDTO;
import com.loctek.workflow.entity.activiti.impl.OvertimeInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.OvertimeTaskVariable;
import com.loctek.workflow.listener.IListener;
import com.loctek.workflow.service.impl.OvertimeProcInstService;
import com.loctek.workflow.service.impl.OvertimeTaskService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("overtime")
@Slf4j
public class OvertimeListener implements IListener {
    private final OvertimeProcInstService overtimeProcInstService;
    private final OvertimeTaskService overtimeTaskService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final TaskService taskService;

    public OvertimeListener(@Lazy OvertimeProcInstService overtimeProcInstService,
                            @Lazy OvertimeTaskService overtimeTaskService,
                            @Lazy ObjectMapper objectMapper,
                            @Lazy RestTemplate restTemplate,
                            @Lazy TaskService taskService) {
        this.overtimeProcInstService = overtimeProcInstService;
        this.overtimeTaskService = overtimeTaskService;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.taskService = taskService;
    }

    @Override
    public void executeOnProcessComplete(String instanceId) {
        BaseProcessInstanceDTO<OvertimeInstanceVariable> historicInst = overtimeProcInstService.getHistoricProcessInstanceByInstanceId(instanceId);
        String businessKey = historicInst.getBusinessKey();
        BaseTaskDTO<OvertimeTaskVariable> lastTask = overtimeTaskService.getLastTaskByBusinessKey(businessKey);
        Boolean approval = taskService.getVariableLocal(lastTask.getId(), "approval", Boolean.class);
        if (approval) {
            log.info("执行完成且通过，调用接口写入，bk：{}", businessKey);
            String body = restTemplate.getForEntity("http://192.168.0.239:18888/salary/api/overtimeApi.do?method=applyLeaveToFeiShu&id=" + businessKey, String.class).getBody();
            log.info("响应:{}",body);
            try {
                Assert.isTrue(objectMapper.readTree(body).get("code").asInt(500) == 200);
                log.info("写入完成");
            } catch (Exception e) {
                log.error("调用出错了嗯...", e);
            }
        }
    }

    @Override
    public void executeOnTaskComplete(String instanceId) {

    }
}
