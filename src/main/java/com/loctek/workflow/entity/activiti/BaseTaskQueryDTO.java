package com.loctek.workflow.entity.activiti;

import com.loctek.workflow.constant.Position;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotNull;

@Data
public class BaseTaskQueryDTO {
    String taskId;
    String auditorId;
    String applierId;
    Position position;
    String businessKey;
    @NotNull
    Boolean isFinished;
}
