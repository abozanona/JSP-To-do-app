package com.cotede.todolist;

public class Ticket {
	private int id;
	private String value;
	private boolean isChecked;
	public Ticket(int id, String value, boolean isChecked) {
		super();
		this.id = id;
		this.value = value;
		this.isChecked = isChecked;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
}
