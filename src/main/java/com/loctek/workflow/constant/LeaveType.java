package com.loctek.workflow.constant;

/**
 * 请假类型
 */
public enum LeaveType {
	wedding("婚假"),
	maternity("产假"),
	breastfeeding("哺乳假"),
	prenatal("产检假"),
	paternity("陪产假"),
	annual("年假"),
	bereavement("丧假"),
	sick("病假"),
	lieu("调休"),
	injury("工伤"),
	personal("事假");

	private final String description;

	LeaveType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
