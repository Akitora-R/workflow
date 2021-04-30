// "All History is current."
//                      --Alice Walker
package com.loctek.workflow.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.loctek.workflow.entity.activiti.BaseActivityDTO;
import com.loctek.workflow.entity.activiti.BaseTaskDTO;
import com.loctek.workflow.entity.activiti.BaseTaskVariable;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BaseActivityService<V extends BaseTaskVariable> {
    protected final HistoryService historyService;
    protected final BaseTaskService<V> taskService;

    /**
     * 按bk获取全部activity历史
     *
     * @param businessKey bk
     * @return 全部activity历史
     */
    public List<BaseActivityDTO<V>> getActivityByBusinessKey(String businessKey) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        Assert.notNull(historicProcessInstance);
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(historicProcessInstance.getId())
                .orderByHistoricActivityInstanceStartTime().asc().list();
        return list.stream().map(h -> {
            BaseTaskDTO<V> task = taskService.getTaskDTOByActivityInstanceId(h.getId());
            return getDTO(h, task);
        }).collect(Collectors.toList());
    }

    protected BaseActivityDTO<V> getDTO(HistoricActivityInstance historicActivityInstance, BaseTaskDTO<V> taskDTO) {
        BaseActivityDTO<V> dto = new BaseActivityDTO<>();
        dto.setId(historicActivityInstance.getId());
        dto.setName(historicActivityInstance.getActivityName());
        dto.setType(historicActivityInstance.getActivityType());
        dto.setStartTime(DateUtil.toLocalDateTime(historicActivityInstance.getStartTime()));
        dto.setEndTime(DateUtil.toLocalDateTime(historicActivityInstance.getEndTime()));
        dto.setTask(taskDTO);
        return dto;
    }
}
