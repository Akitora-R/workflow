package com.loctek.workflow.controller.proc;

import com.loctek.workflow.constant.AuditStatus;
import com.loctek.workflow.entity.Resp;
import com.loctek.workflow.entity.activiti.*;
import com.loctek.workflow.entity.activiti.impl.LeaveInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.LeaveProcessInstanceInitDTO;
import com.loctek.workflow.entity.activiti.impl.LeaveTaskVariable;
import com.loctek.workflow.service.impl.LeaveActService;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import com.loctek.workflow.service.impl.LeaveTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
@Validated
public class LeaveController {
    private final LeaveActService leaveActService;
    private final LeaveProcInstService leaveProcInstService;
    private final LeaveTaskService leaveTaskService;

    //FIXME: 2021/4/27 通过接口获取或者开始任务时传入
    private final static Map<String, List<String>> groupService = Collections.unmodifiableMap(new HashMap<String, List<String>>() {{
        put("SupervisorCandidateList", Arrays.asList("s1", "s2"));
        put("ManagerCandidateList", Arrays.asList("m1", "m2"));
        put("DirectorCandidateList", Arrays.asList("d1", "d2"));
        put("VicePresidentCandidateList", Arrays.asList("v1", "v2"));
        put("PresidentCandidateList", Arrays.asList("p1", "p2"));
    }});

    @GetMapping("/proc/def")
    public Resp<?> getInsDef() {
        return Resp.success(null, leaveProcInstService.getProcessDefinition());
    }

    @PostMapping("/proc/start")
    public Resp<?> startProcInsByBusinessKey(@RequestBody @Validated LeaveProcessInstanceInitDTO dto) {
        LeaveInstanceVariable instanceVariables =
                new LeaveInstanceVariable(
                        dto.getApplierId(),
                        dto.getApplierGroup(),
                        dto.getApplierLevel(),
                        dto.getDays(),
                        groupService.get("SupervisorCandidateList"),
                        groupService.get("ManagerCandidateList"),
                        groupService.get("DirectorCandidateList"),
                        groupService.get("VicePresidentCandidateList"),
                        groupService.get("PresidentCandidateList"));
        ProcessInstanceInitBO<LeaveInstanceVariable> initBO =
                new ProcessInstanceInitBO<>(leaveProcInstService.getDefinitionKey(), dto.getBusinessKey(), instanceVariables);
        return Resp.success(null, leaveProcInstService.startProcessInstance(initBO));
    }

    @GetMapping("/proc/ins/all")
    public Resp<?> getActiveLeaveInsList() {
        return Resp.success(null, leaveProcInstService.getActiveProcessInstanceList());
    }

    @DeleteMapping("/proc/ins/{id}")
    public ResponseEntity<Resp<?>> delInsById(@PathVariable String id, @RequestParam String reason) {
        return leaveProcInstService.deleteProcessInstance(id, reason) ?
                new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("已删除或ID不正确", null), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/task/complete")
    public ResponseEntity<Resp<?>> completeTask(@RequestBody @Validated BaseTaskConclusionDTO<LeaveTaskVariable> dto) {
        return leaveTaskService.completeTask(dto) ?
                new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("已完成或ID不正确", null), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/task/bk/{businessKey}")
    public Resp<?> getTasksByBusinessKey(@PathVariable String businessKey) {
        return Resp.success(null, leaveTaskService.getTaskListByBusinessKey(businessKey));
    }

    @GetMapping("/task/user/{userId}")
    public Resp<?> getTasksByUserId(@PathVariable String userId, @RequestParam(required = false) Boolean isFinished) {
        return Resp.success(null, leaveTaskService.getTaskListByUser(userId, isFinished));
    }

    @PostMapping("/task/bk")
    public Resp<?> getAuditStateByBusinessKey(@RequestBody List<String> businessKeyList) {
        Map<String, AuditStatusDTO> map = businessKeyList.stream().map(bk -> {
            BaseTaskDTO<LeaveTaskVariable> lastTask = leaveTaskService.getLastTaskByBusinessKey(bk);
            boolean notSubmitted = lastTask == null;
            AuditStatus status =
                    notSubmitted ? AuditStatus.notSubmitted :
                            !lastTask.getFinished() ? AuditStatus.pending :
                                    lastTask.getApproval() ? AuditStatus.accepted : AuditStatus.rejected;
            return new AuditStatusDTO(bk, notSubmitted ? null : lastTask.getId(), status, "");
        }).collect(Collectors.toMap(AuditStatusDTO::getBusinessKey, dto -> dto));
        return Resp.success(null, map);
    }

    @GetMapping("/act/bk/{businessKey}")
    public ResponseEntity<Resp<?>> getActivityByBusinessKey(@PathVariable String businessKey) {
        List<BaseActivityDTO<LeaveTaskVariable>> activity = leaveActService.getActivityByBusinessKey(businessKey);
        return !activity.isEmpty() ?
                new ResponseEntity<>(Resp.success(null, activity), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("无可用数据", null), HttpStatus.NOT_FOUND);
    }
}
