package com.loctek.workflow.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderExtraVariables implements IBaseExtraVariables{
    @JsonIgnore
    private Double total;
    @JsonIgnore
    private List<String> managerList;
    @JsonIgnore
    private List<String> bossList;

    @SuppressWarnings("unchecked")
    public OrderExtraVariables(Map<String,Object> variables) {
        this.total = (Double) variables.get("total");
        this.managerList = (List<String>) variables.get("managerList");
        this.bossList = (List<String>) variables.get("bossList");
    }

    @Override
    public Map<String, Object> getTaskExtraVariables() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Object> getInstanceExtraVariables() {
        return new HashMap<String, Object>(){{
            put("total",total);
            put("managerList",managerList);
            put("bossList",bossList);
        }};
    }
}
