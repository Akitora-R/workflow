package com.loctek.workflow.entity.activiti.impl;

import com.loctek.workflow.entity.activiti.BaseTaskVariable;

import java.util.Collections;
import java.util.Map;

public class LeaveTaskVariable extends BaseTaskVariable {

    @Override
    protected Map<String, Object> additionalVariable() {
        return Collections.emptyMap();
    }
}
