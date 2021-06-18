package com.loctek.workflow.controller.proc;

import com.loctek.workflow.entity.activiti.impl.OvertimeInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.OvertimeProcessInstanceInitDTO;
import com.loctek.workflow.entity.activiti.impl.OvertimeTaskVariable;
import com.loctek.workflow.service.impl.OvertimeProcInstService;
import com.loctek.workflow.service.impl.OvertimeTaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/overtime")
public class OvertimeController extends BaseProcessController<OvertimeTaskVariable, OvertimeInstanceVariable, OvertimeProcessInstanceInitDTO, OvertimeTaskService, OvertimeProcInstService> {

    public OvertimeController(OvertimeTaskService taskService, OvertimeProcInstService instService) {
        super(taskService, instService);
    }

    @Override
    protected OvertimeInstanceVariable getVariablesByInitDTO(OvertimeProcessInstanceInitDTO dto) {
        return new OvertimeInstanceVariable(
                dto.getApplierId(),
                dto.getApplierDepartmentId(),
                dto.getApplierGroup(),
                dto.getApplierLevel(),
                dto.getSupervisorCandidateList(),
                dto.getManagerCandidateList(),
                dto.getDirectorCandidateList(),
                dto.getVicePresidentCandidateList(),
                dto.getPresidentCandidateList()
        );
    }
}
