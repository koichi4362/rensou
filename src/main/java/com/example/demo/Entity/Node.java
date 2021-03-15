package com.example.demo.Entity;

public class Node {
	private Integer node_id;
	private String node_name;
	private Integer parent_id;
	private Integer sheet_id;

	public Node() {

	}

	public Node(String node_name, Integer sheet_id) {
		this.node_name = node_name;
		this.sheet_id = sheet_id;
	}

	public Node(String node_name, Integer sheet_id, Integer parent_id) {
		this.node_name = node_name;
		this.sheet_id = sheet_id;
		this.parent_id = parent_id;
	}

	public Integer getNode_id() {
		return node_id;
	}

	public void setNode_id(Integer node_id) {
		this.node_id = node_id;
	}

	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getSheet_id() {
		return sheet_id;
	}

	public void setSheet_id(Integer sheet_id) {
		this.sheet_id = sheet_id;
	}

}
