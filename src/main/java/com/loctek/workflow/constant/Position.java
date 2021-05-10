package com.loctek.workflow.constant;

public enum Position {
    supervisor("主管"),
    manager("经理"),
    director("总监"),
    vicePresident("副总裁"),
    president("总裁"),
    ;

    private final String desc;

    Position(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
