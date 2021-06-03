package com.revature.models;

public enum Status {
	PENDING(1, "pending"), APPROVED(2, "approved"), DENIED(3, "denied");

	private Integer id;
	private String sname;

	Status(Integer id, String sname) {
		this.id = id;
		this.sname = sname;
	}

	public Integer id() {
		return this.id;
	}

	public String sname() {
		return this.sname;
	}

}
