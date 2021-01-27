package com.example.demo.Entity;

public class Sheet {

	private Integer sheet_id;
	private Integer user_id;
	private Integer last_node_id;
	private String sheet_name;
	private Integer public_flag;

	public Integer getSheet_id() {
		return sheet_id;
	}

	public void setSheet_id(Integer sheet_id) {
		this.sheet_id = sheet_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getLast_node_id() {
		return last_node_id;
	}

	public void setLast_node_id(Integer last_node_id) {
		this.last_node_id = last_node_id;
	}

	public String getSheet_name() {
		return sheet_name;
	}

	public void setSheet_name(String sheet_name) {
		this.sheet_name = sheet_name;
	}

	public Integer getPublic_flag() {
		return public_flag;
	}

	public void setPublic_flag(Integer public_flag) {
		this.public_flag = public_flag;
	}

}
