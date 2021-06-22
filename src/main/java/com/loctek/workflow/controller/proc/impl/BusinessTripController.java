package com.loctek.workflow.controller.proc.impl;

import com.loctek.workflow.controller.proc.BaseProcessController;
import com.loctek.workflow.entity.activiti.impl.BusinessTripInstanceVariable;
import com.loctek.workflow.entity.activiti.impl.BusinessTripProcessInstanceInitDTO;
import com.loctek.workflow.entity.activiti.impl.BusinessTripTaskVariable;
import com.loctek.workflow.service.impl.BusinessTripProcInstService;
import com.loctek.workflow.service.impl.BusinessTripTaskService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/businessTrip")
public class BusinessTripController extends BaseProcessController<
        BusinessTripTaskVariable,
        BusinessTripInstanceVariable,
        BusinessTripProcessInstanceInitDTO,
        BusinessTripTaskService,
        BusinessTripProcInstService> {
    public BusinessTripController(BusinessTripTaskService taskService, BusinessTripProcInstService instService) {
        super(taskService, instService);
    }

    @Override
    protected BusinessTripInstanceVariable getVariablesByInitDTO(BusinessTripProcessInstanceInitDTO dto) {
        return new BusinessTripInstanceVariable(
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
