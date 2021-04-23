package com.loctek.workflow.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.loctek.workflow.entity.dto.BaseActivityDTO;
import com.loctek.workflow.entity.dto.BaseTaskDTO;
import com.loctek.workflow.entity.dto.IBaseExtraTaskVariables;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;

import java.util.List;
import java.util.stream.Collectors;

// "All History is current."
//                      --Alice Walker

@RequiredArgsConstructor
public abstract class IActivityService<V extends IBaseExtraTaskVariables> {
    private final HistoryService historyService;
    private final ITaskService<V> taskService;

    /**
     * 按bk获取全部activity历史
     *
     * @param businessKey bk
     * @return 全部activity历史
     */
    public List<BaseActivityDTO<V>> getActivityByBusinessKey(String businessKey){
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        Assert.notNull(historicProcessInstance);
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(historicProcessInstance.getId())
                .orderByHistoricActivityInstanceStartTime().asc().list();
        return list.stream().map(h -> {
            BaseTaskDTO<V> task = taskService.getTaskByActivityId(h.getActivityId());
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
