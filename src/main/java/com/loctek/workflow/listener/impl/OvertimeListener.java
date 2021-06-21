package com.loctek.workflow.listener.impl;

import com.loctek.workflow.listener.IListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("overtime")
@Slf4j
public class OvertimeListener implements IListener {
    @Override
    public void executeOnProcessComplete(String instanceId) {
        log.info("加班流程结束,id:{}", instanceId);
    }

    @Override
    public void executeOnTaskComplete(String instanceId) {

    }
}
