package com.loctek.workflow.config;

import com.loctek.workflow.listener.ActivitiGlobalEventListener;
import lombok.RequiredArgsConstructor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {
    private final ActivitiGlobalEventListener activitiGlobalEventListener;

    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setEventListeners(Collections.singletonList(activitiGlobalEventListener));
    }
}
