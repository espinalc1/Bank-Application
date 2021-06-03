package com.revature.models;

public enum ReimType {
	LODGING(1, "lodging"), TRAVEL(2, "travel"), FOOD(3, "food"), OTHER(4, "other");

	private Integer id;
	private String type;

	ReimType(Integer id, String type) {
		this.id = id;
		this.type = type;
	}

	public Integer id() {
		return this.id;
	}

	String type() {
		return this.type;
	}
	
	
}
