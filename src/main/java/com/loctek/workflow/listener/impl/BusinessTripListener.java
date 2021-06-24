package com.loctek.workflow.listener.impl;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loctek.workflow.entity.activiti.BaseProcessInstanceDTO;
import com.loctek.workflow.entity.activiti.BaseTaskDTO;
import com.loctek.workflow.entity.activiti.impl.BusinessTripInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.BusinessTripTaskVariable;
import com.loctek.workflow.listener.IListener;
import com.loctek.workflow.service.impl.BusinessTripProcInstService;
import com.loctek.workflow.service.impl.BusinessTripTaskService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("businessTrip")
@Slf4j
public class BusinessTripListener implements IListener {
    private final BusinessTripProcInstService businessTripProcInstService;
    private final BusinessTripTaskService businessTripTaskService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final TaskService taskService;

    public BusinessTripListener(@Lazy BusinessTripProcInstService businessTripProcInstService,
                            @Lazy BusinessTripTaskService businessTripTaskService,
                            @Lazy ObjectMapper objectMapper,
                            @Lazy RestTemplate restTemplate,
                            @Lazy TaskService taskService) {
        this.businessTripProcInstService = businessTripProcInstService;
        this.businessTripTaskService = businessTripTaskService;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.taskService = taskService;
    }
    @Override
    public void executeOnProcessComplete(String instanceId) {
        BaseProcessInstanceDTO<BusinessTripInstanceVariable> historicInst = businessTripProcInstService.getHistoricProcessInstanceByInstanceId(instanceId);
        String businessKey = historicInst.getBusinessKey();
        BaseTaskDTO<BusinessTripTaskVariable> lastTask = businessTripTaskService.getLastTaskByBusinessKey(businessKey);
        Boolean approval = taskService.getVariableLocal(lastTask.getId(), "approval", Boolean.class);
        if (approval) {
            log.info("执行完成且通过，调用接口写入，bk：{}", businessKey);
            String body = restTemplate.getForEntity("http://192.168.0.239:18888/salary/api/businessTripApi.do?method=applyToFeiShu&id=" + businessKey, String.class).getBody();
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
