package com.loctek.workflow.service.impl;

import com.loctek.workflow.entity.activiti.impl.LeaveTaskVariable;
import com.loctek.workflow.service.BaseTaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.springframework.stereotype.Service;

@Service
public class LeaveTaskService extends BaseTaskService<LeaveTaskVariable> {
    public LeaveTaskService(HistoryService historyService, TaskService taskService) {
        super(historyService, taskService);
    }

//    public PagedData<BaseTaskDTO<LeaveTaskVariable>> getTaskListByQuery(Integer pageNo, Integer pageSize, LeaveTaskQueryDTO taskQueryDTO) {
//        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
//        if (taskQueryDTO.getIsFinished() != null) {
//            if (taskQueryDTO.getIsFinished()) {
//                query.finished();
//            } else {
//                query.unfinished();
//            }
//        }
//        if (taskQueryDTO.getPosition() != null) {
//            query.taskName(taskQueryDTO.getPosition().getDesc().concat("审核"));
//        }
//        if (StrUtil.isNotBlank(taskQueryDTO.getApplierId())) {
//            query.taskVariableValueEquals("applierId", taskQueryDTO.getApplierId());
//        }
//        if (StrUtil.isNotBlank(taskQueryDTO.getAuditorId())) {
//            query.or()
//                    .taskCandidateUser(taskQueryDTO.getAuditorId())
//                    .taskAssignee(taskQueryDTO.getAuditorId())
//                    .endOr();
//        }
//        if (StrUtil.isNotBlank(taskQueryDTO.getBusinessKey())) {
//            query.processInstanceBusinessKey(taskQueryDTO.getBusinessKey());
//        }
//        query.orderByTaskCreateTime().desc();
//        long totalCount = query.count();
//        List<BaseTaskDTO<LeaveTaskVariable>> dtoList = getDTOListByInstanceList(query.listPage((pageNo - 1) * pageSize, pageNo * pageSize));
//        return new PagedData<>(pageNo, pageSize, dtoList.size(), totalCount, totalCount > dtoList.size(), dtoList);
//    }
}
