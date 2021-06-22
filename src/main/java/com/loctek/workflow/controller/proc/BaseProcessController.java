package com.loctek.workflow.controller.proc;

import com.loctek.workflow.entity.PagedData;
import com.loctek.workflow.entity.Resp;
import com.loctek.workflow.entity.activiti.*;
import com.loctek.workflow.service.BaseProcessInstanceService;
import com.loctek.workflow.service.BaseTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@Validated
public abstract class BaseProcessController<
        TV extends BaseTaskVariable,
        IV extends BaseInstanceVariable,
        ID extends BaseProcessInstanceInitDTO,
        TS extends BaseTaskService<TV>,
        IS extends BaseProcessInstanceService<IV>
        > {
    protected final TS taskService;
    protected final IS instService;

    @PostMapping("/proc/start")
    public Resp<BaseProcessInstanceDTO<?>> startProcInsByBusinessKey(@RequestBody @Validated ID dto){
        ProcessInstanceInitBO<IV> initBO =
                new ProcessInstanceInitBO<>(instService.getDefinitionKey(), dto.getBusinessKey(), getVariablesByInitDTO(dto));
        return Resp.success(null, instService.startProcessInstance(initBO));
    }

    protected abstract IV getVariablesByInitDTO(ID dto);

    @GetMapping("/proc/ins/all")
    public Resp<?> getActiveInstList() {
        return Resp.success(null, instService.getActiveProcessInstanceList());
    }

    @DeleteMapping("/proc/ins/bk/{bk}")
    public ResponseEntity<Resp<?>> delInsByBk(@PathVariable String bk, @RequestParam(required = false) String reason) {
        BaseProcessInstanceDTO<?> instance = instService.getProcessInstanceByBusinessKey(bk);
        if (instance == null) {
            return new ResponseEntity<>(Resp.fail("已删除或ID不正确", null), HttpStatus.NOT_FOUND);
        }
        instService.deleteProcessInstance(instance.getId(), reason);
        return new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK);
    }

    @PutMapping("/task/complete")
    public ResponseEntity<Resp<?>> completeTask(@RequestBody @Validated BaseTaskConclusionDTO<TV> dto) {
        return taskService.completeTask(dto) ?
                new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("已完成或ID不正确", null), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/task/complete/name")
    public ResponseEntity<Resp<?>> completeTaskByName(@RequestBody @Validated BaseTaskConclusionDTO<TV> dto,
                                                      @RequestParam @NotBlank String name,
                                                      @RequestParam @NotBlank String businessKey) {
        return taskService.completeTaskByName(dto,name,businessKey) ?
                new ResponseEntity<>(Resp.success(null, null), HttpStatus.OK) :
                new ResponseEntity<>(Resp.fail("已完成或ID不正确", null), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/task/bk/{businessKey}")
    public Resp<List<BaseTaskDTO<TV>>> getTasksByBusinessKey(@PathVariable String businessKey) {
        return Resp.success(null, taskService.getTaskListByBusinessKey(businessKey));
    }

    @GetMapping("/task/bk/{businessKey}/{taskId}")
    public Resp<List<BaseTaskDTO<TV>>> getSingleProcTasksByBusinessKey(@PathVariable String businessKey, @PathVariable String taskId) {
        String instId = taskService.getInstIdByTaskId(taskId);
        return Resp.success(null, taskService.getTaskListByBusinessKeyAndInstanceId(businessKey,instId));
    }

    @PostMapping("/task/query")
    public Resp<PagedData<BaseTaskDTO<TV>>> getTasksByQuery(@RequestParam(defaultValue = "1") Integer pageNo,
                                                                           @Max(100) @RequestParam(defaultValue = "40") Integer pageSize,
                                                                           @RequestBody@Validated BaseTaskQueryDTO queryDTO) {
        return Resp.success(null, taskService.getTaskListByQuery(pageNo, pageSize, queryDTO));
    }

    @PostMapping("/task/bkList")
    public Resp<?> getAuditStateByBusinessKeyList(@RequestBody List<String> businessKeyList) {
        return Resp.success(null, taskService.getAuditStatusByBusinessKeyList(businessKeyList));
    }

}
