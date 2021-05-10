package com.loctek.workflow.listener;

public interface IListener {
    void executeOnProcessComplete(String instanceId);
    void executeOnTaskComplete(String executionId);
}
