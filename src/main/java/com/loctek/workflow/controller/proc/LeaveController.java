package com.loctek.workflow.controller.proc;

import com.loctek.workflow.entity.Resp;
import com.loctek.workflow.entity.dto.ProcessInstanceInitBO;
import com.loctek.workflow.entity.dto.impl.LeaveExtraInstanceVariables;
import com.loctek.workflow.entity.dto.impl.LeaveProcessInstanceInitDTO;
import com.loctek.workflow.service.impl.LeaveActService;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import com.loctek.workflow.service.impl.LeaveTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
@Validated
public class LeaveController {
    private final LeaveActService leaveActService;
    private final LeaveProcInstService leaveProcInstService;
    private final LeaveTaskService leaveTaskService;

    private final static Map<String, List<String>> groupService = Collections.unmodifiableMap(new HashMap<String, List<String>>() {{
        put("SupervisorCandidateList", Arrays.asList("s1", "s2"));
        put("ManagerCandidateList", Arrays.asList("m1", "m2"));
        put("DirectorCandidateList", Arrays.asList("d1", "d2"));
    }});

    @GetMapping("/proc/def")
    public Resp<?> getInsDef() {
        return Resp.success(null, leaveProcInstService.getProcessDefinition());
    }

    @PostMapping("/proc/start")
    public Resp<?> startProcInsByBusinessKey(@RequestBody @Validated LeaveProcessInstanceInitDTO dto) {
        LeaveExtraInstanceVariables instanceVariables =
                new LeaveExtraInstanceVariables(
                        dto.getApplierGroup(),
                        dto.getApplierLevel(),
                        dto.getDays(),
                        groupService.get("SupervisorCandidateList"),
                        groupService.get("ManagerCandidateList"),
                        groupService.get("DirectorCandidateList"));
        ProcessInstanceInitBO<LeaveExtraInstanceVariables> initBO =
                new ProcessInstanceInitBO<>(leaveProcInstService.getDefinitionKey(), dto.getBusinessKey(), dto.getApplierId(), instanceVariables);
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
}
