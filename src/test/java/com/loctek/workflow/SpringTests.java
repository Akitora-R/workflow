package com.loctek.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loctek.workflow.entity.activiti.BaseProcessInstanceDTO;
import com.loctek.workflow.entity.activiti.BaseTaskDTO;
import com.loctek.workflow.entity.activiti.ProcessInstanceInitBO;
import com.loctek.workflow.entity.activiti.impl.LeaveInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.LeaveTaskVariable;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import com.loctek.workflow.service.impl.LeaveTaskService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
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
    @Autowired
    LeaveTaskService leaveTaskService;
    @Test
    void contextLoads() {
    }

    @Test
    void t1(){
        Task t = taskService.createTaskQuery()
                .taskName("经理审核")
                .processInstanceBusinessKey("2c9097127a2d02a4017a2d0cde860002")
                .singleResult();
        System.out.println(t);
    }

    @Test
    void deployment(){
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/leave.bpmn")
                .addClasspathResource("processes/leave.png")
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
        put("supervisorCandidateList", Arrays.asList("s1", "s2"));
        put("managerCandidateList", Arrays.asList("m1", "m2"));
        put("directorCandidateList", Arrays.asList("d1", "d2"));
    }});

    @Test
    void startProc(){
        LeaveInstanceVariable instanceVariables =
                new LeaveInstanceVariable(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        "技术",
                        5,
                        0.5,
                        groupService.get("supervisorCandidateList"),
                        groupService.get("managerCandidateList"),
                        groupService.get("directorCandidateList"),null,null);
        ProcessInstanceInitBO<LeaveInstanceVariable> initBO =
                new ProcessInstanceInitBO<>("leave", UUID.randomUUID().toString(), instanceVariables);
        log.info(String.valueOf(initBO));
        leaveProcInstService.startProcessInstance(initBO);
    }

    @Test
    void getHisTaskVar() throws JsonProcessingException {
        List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId("1e38d198-a7c8-11eb-b8d0-c4651636fef4").list();

    }

    @Test
    void completeTask(){
        taskService.complete("b0a460cd-a735-11eb-9845-c4651636fef4",new HashMap<String,Object>(){{
            put("approval",true);
            put("comment","经理备注经理备注经理备注经理备注经理备注");
        }});
    }
    @Test
    void getIdentityLink(){
        String taskId="001749f2-a89d-11eb-8a85-c4651636fef4";
        historyService.getHistoricIdentityLinksForTask(taskId);
    }

    @Test
    void getHistoricProcInstDTO() throws JsonProcessingException {
        String id="2c9097127a13f6e1017a14277617000e";

        System.out.println(leaveTaskService.getLastTaskByBusinessKey(id).getApproval());
    }

    private void printJson(Object o){
        try {
            System.out.println(objectMapper.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
