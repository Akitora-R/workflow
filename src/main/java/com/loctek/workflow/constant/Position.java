package com.loctek.workflow.constant;

public enum Position {
    Supervisor("主管"),
    Manager("经理"),
    Director("总监"),
    VicePresident("副总裁"),
    President("总裁"),
    ;

    private final String desc;

    Position(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
