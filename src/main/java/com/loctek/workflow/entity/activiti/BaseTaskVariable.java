package com.loctek.workflow.entity.activiti;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务需要的额外变量, 自行重写
 */
@Data
public abstract class BaseTaskVariable {
    @NotNull
    Boolean approval;
    String comment;

    protected abstract Map<String, Object> additionalVariable();

    public Map<String, Object> toMap() {
        HashMap<String, Object> m = new HashMap<>();
        m.put("comment", comment);
        m.put("approval", approval);
        m.putAll(additionalVariable());
        return m;
    }
}
