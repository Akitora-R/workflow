package com.loctek.workflow.constant;

public enum Position {
    supervisor("主管"),
    manager("经理"),
    director("总监"),
    vicePresident("副总经理"),
    president("总经理"),
    hrCommissioner("人事专员"),
    hrManager("人事经理"),
    ;

    private final String desc;

    Position(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
