package com.loctek.workflow.controller.proc.impl;

import com.loctek.workflow.controller.proc.BaseProcessController;
import com.loctek.workflow.entity.activiti.impl.LeaveInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.LeaveProcessInstanceInitDTO;
import com.loctek.workflow.entity.activiti.impl.LeaveTaskVariable;
import com.loctek.workflow.service.impl.LeaveProcInstService;
import com.loctek.workflow.service.impl.LeaveTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
@Slf4j
public class LeaveController extends BaseProcessController<
        LeaveTaskVariable,
        LeaveInstanceVariable,
        LeaveProcessInstanceInitDTO,
        LeaveTaskService,
        LeaveProcInstService> {

    public LeaveController(LeaveTaskService taskService, LeaveProcInstService instService) {
        super(taskService, instService);
    }

    @Override
    protected LeaveInstanceVariable getVariablesByInitDTO(LeaveProcessInstanceInitDTO dto) {
        return new LeaveInstanceVariable(
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
    }
}
