package com.loctek.workflow.controller.proc;

import com.loctek.workflow.entity.PagedData;
import com.loctek.workflow.entity.Resp;
import com.loctek.workflow.entity.activiti.*;
import com.loctek.workflow.entity.activiti.impl.*;
import com.loctek.workflow.service.impl.LeaveActService;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import com.loctek.workflow.service.impl.LeaveTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
@Validated
public class LeaveController {
    private final LeaveActService leaveActService;
    private final LeaveProcInstService leaveProcInstService;
    private final LeaveTaskService leaveTaskService;

    @GetMapping("/proc/def")
    public Resp<?> getInsDef() {
        return Resp.success(null, leaveProcInstService.getProcessDefinition());
    }

    @PostMapping("/proc/start")
    public Resp<BaseProcessInstanceDTO<LeaveInstanceVariable>> startProcInsByBusinessKey(@RequestBody @Validated LeaveProcessInstanceInitDTO dto) {
        LeaveInstanceVariable instanceVariables =
                new LeaveInstanceVariable(
                        dto.getApplierId(),
                        dto.getApplierDepartmentId(),
                        dto.getApplierGroup(),
                        dto.getApplierLevel(),
                        dto.getDays(),
                        dto.getSupervisorCandidateList(),
                        dto.getManagerCandidateList(),
                        dto.getDirectorCandidateList(),
                        dto.getVicePresidentCandidateList(),
                        dto.getPresidentCandidateList());
        ProcessInstanceInitBO<LeaveInstanceVariable> initBO =
                new ProcessInstanceInitBO<>(leaveProcInstService.getDefinitionKey(), dto.getBusinessKey(), instanceVariables);
        return Resp.success(null, leaveProcInstService.startProcessInstance(initBO));
    }

    @GetMapping("/proc/ins/all")
    public Resp<?> getActiveLeaveInsList() {
        return Resp.success(null, leaveProcInstService.getActiveProcessInstanceList());
    }

    @DeleteMapping("/proc/ins/id/{id}")
    public ResponseEntity<Resp<?>> delInsById(@PathVariable String id, @RequestParam(required = false) String reason) {
        return leaveProcInstService.deleteProcessInstance(id, reason) ?
                new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("已删除或ID不正确", null), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/proc/ins/bk/{bk}")
    public ResponseEntity<Resp<?>> delInsByBk(@PathVariable String bk, @RequestParam(required = false) String reason) {
        // FIXME: 2021-05-07 非空判断尚有一些问题
        BaseProcessInstanceDTO<LeaveInstanceVariable> instance = leaveProcInstService.getProcessInstanceByBusinessKey(bk);
        if (instance == null) {
            return new ResponseEntity<>(Resp.fail("已删除或ID不正确", null), HttpStatus.NOT_FOUND);
        }
        leaveProcInstService.deleteProcessInstance(instance.getId(), reason);
        return new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK);
    }

    @PostMapping("/proc/ins/query")
    public Resp<PagedData<?>> getInstancesByQuery(@RequestParam(defaultValue = "1") Integer pageNo,
                                                  @Max(100) @RequestParam(defaultValue = "40") Integer pageSize,
                                                  @RequestBody LeaveInstanceQueryDTO queryDTO) {
        return Resp.success(null, leaveProcInstService.getInstanceListByQuery(pageNo, pageSize, queryDTO));
    }

    @PutMapping("/task/complete")
    public ResponseEntity<Resp<?>> completeTask(@RequestBody @Validated BaseTaskConclusionDTO<LeaveTaskVariable> dto) {
        return leaveTaskService.completeTask(dto) ?
                new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("已完成或ID不正确", null), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/task/complete/name")
    public ResponseEntity<Resp<?>> completeTaskByName(@RequestBody @Validated BaseTaskConclusionDTO<LeaveTaskVariable> dto,
                                                      @RequestParam @NotBlank String name,
                                                      @RequestParam @NotBlank String businessKey) {
        return leaveTaskService.completeTaskByName(dto,name,businessKey) ?
                new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("已完成或ID不正确", null), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/task/bk/{businessKey}")
    public Resp<List<BaseTaskDTO<LeaveTaskVariable>>> getTasksByBusinessKey(@PathVariable String businessKey) {
        return Resp.success(null, leaveTaskService.getTaskListByBusinessKey(businessKey));
    }

    @PostMapping("/task/query")
    public Resp<PagedData<BaseTaskDTO<LeaveTaskVariable>>> getTasksByQuery(@RequestParam(defaultValue = "1") Integer pageNo,
                                                                           @Max(100) @RequestParam(defaultValue = "40") Integer pageSize,
                                                                           @RequestBody@Validated LeaveTaskQueryDTO queryDTO) {
        return Resp.success(null, leaveTaskService.getTaskListByQuery(pageNo, pageSize, queryDTO));
    }

    @PostMapping("/task/bkList")
    public Resp<?> getAuditStateByBusinessKeyList(@RequestBody List<String> businessKeyList) {
        return Resp.success(null, leaveTaskService.getAuditStatusByBusinessKeyList(businessKeyList));
    }

    @GetMapping("/act/bk/{businessKey}")
    public ResponseEntity<Resp<?>> getActivityByBusinessKey(@PathVariable String businessKey) {
        List<BaseActivityDTO<LeaveTaskVariable>> activity = leaveActService.getActivityByBusinessKey(businessKey);
        return !activity.isEmpty() ?
                new ResponseEntity<>(Resp.success(null, activity), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("无可用数据", null), HttpStatus.NOT_FOUND);
    }
}
