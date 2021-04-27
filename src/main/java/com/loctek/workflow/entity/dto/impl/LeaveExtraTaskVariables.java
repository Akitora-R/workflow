package com.loctek.workflow.entity.dto.impl;

import com.loctek.workflow.entity.dto.IBaseExtraTaskVariables;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LeaveExtraTaskVariables implements IBaseExtraTaskVariables {
    @Override
    public Map<String, Object> getVariables() {
        return Collections.emptyMap();
    }

    @Override
    public List<String> getVariableFields() {
        return Collections.emptyList();
    }
}
