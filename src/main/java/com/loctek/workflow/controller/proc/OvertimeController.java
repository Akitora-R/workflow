package com.loctek.workflow.controller.proc;

import com.loctek.workflow.entity.Resp;
import com.loctek.workflow.entity.activiti.BaseProcessInstanceDTO;
import com.loctek.workflow.entity.activiti.ProcessInstanceInitBO;
import com.loctek.workflow.entity.activiti.impl.LeaveInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.OvertimeInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.OvertimeProcessInstanceInitDTO;
import com.loctek.workflow.service.impl.OvertimeProcInstService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/overtime")
@Validated
public class OvertimeController {
    private final OvertimeProcInstService overtimeProcInstService;

    @PostMapping("/proc/start")
    public Resp<BaseProcessInstanceDTO<OvertimeInstanceVariable>> startProcInsByBusinessKey(@RequestBody @Validated OvertimeProcessInstanceInitDTO dto) {
        OvertimeInstanceVariable instanceVariables =
                new OvertimeInstanceVariable(
                        dto.getApplierId(),
                        dto.getApplierDepartmentId(),
                        dto.getApplierGroup(),
                        dto.getApplierLevel(),
                        dto.getSupervisorCandidateList(),
                        dto.getManagerCandidateList(),
                        dto.getDirectorCandidateList(),
                        dto.getVicePresidentCandidateList(),
                        dto.getPresidentCandidateList());
        ProcessInstanceInitBO<OvertimeInstanceVariable> initBO =
                new ProcessInstanceInitBO<>(overtimeProcInstService.getDefinitionKey(), dto.getBusinessKey(), instanceVariables);
        return Resp.success(null, overtimeProcInstService.startProcessInstance(initBO));
    }
}
