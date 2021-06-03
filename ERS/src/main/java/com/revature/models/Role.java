package com.revature.models;

public enum Role {
	MANAGER(2, "manager"), EMPLOYEE(1, "employee"), FINANCE_MANAGER(3, "financial manager");

	private final Integer id;
	private final String rname;

	Role(Integer id, String rname) {
		this.id = id;
		this.rname = rname;
	}

	public Integer id() {
		return this.id;
	}

	public String rname() {
		return this.rname;
	}
}
