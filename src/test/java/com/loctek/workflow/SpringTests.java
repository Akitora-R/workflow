package com.loctek.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loctek.workflow.entity.activiti.ProcessInstanceInitBO;
import com.loctek.workflow.entity.activiti.impl.LeaveExtraInstanceVariables;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
class SpringTests {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    HistoryService historyService;
    @Autowired
    TaskService taskService;
    @Autowired
    LeaveProcInstService leaveProcInstService;
    @Test
    void contextLoads() {
    }

    @Test
    void test1(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/leave.bpmn")
                .addClasspathResource("processes/leave.png")
                .key("leave")
                .name("请假")
                .deploy();
        log.info("id:{},name:{},key:{}",deploy.getId(),deploy.getName(),deploy.getKey());
    }

    @Test
    void getProcDef(){
        repositoryService.createProcessDefinitionQuery().latestVersion().list().forEach(p->{
            log.info("key:{}, name:{}",p.getKey(),p.getName());
        });
    }


    @Test
    void getProcVar(){
        runtimeService.createProcessInstanceQuery().active().list().forEach(processInstance -> {
            log.info("id:{}",processInstance.getId());
        });
    }

    private final static Map<String, List<String>> groupService = Collections.unmodifiableMap(new HashMap<String, List<String>>() {{
        put("SupervisorCandidateList", Arrays.asList("s1", "s2"));
        put("ManagerCandidateList", Arrays.asList("m1", "m2"));
        put("DirectorCandidateList", Arrays.asList("d1", "d2"));
    }});

    @Test
    void startProc(){
        LeaveExtraInstanceVariables instanceVariables =
                new LeaveExtraInstanceVariables(
                        "技术",
                        5,
                        0.5,
                        groupService.get("SupervisorCandidateList"),
                        groupService.get("ManagerCandidateList"),
                        groupService.get("DirectorCandidateList"));
        ProcessInstanceInitBO<LeaveExtraInstanceVariables> initBO =
                new ProcessInstanceInitBO<>("leave", UUID.randomUUID().toString(), UUID.randomUUID().toString(), instanceVariables);
        log.info(String.valueOf(initBO));
        leaveProcInstService.startProcessInstance(initBO);
    }

    @Test
    void getHisTaskVar() throws JsonProcessingException {
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().taskId("2ca9bbfa-a6fd-11eb-bc3a-c4651636fef4").list();
        list.forEach(l->{
            System.out.println(l.getVariableName());
        });
    }

    @Test
    void completeTask(){
        taskService.complete("b0a460cd-a735-11eb-9845-c4651636fef4",new HashMap<String,Object>(){{
            put("approval",true);
            put("comment","经理备注经理备注经理备注经理备注经理备注");
        }});
    }
}
