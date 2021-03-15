package com.example.demo.Entity;

import org.springframework.stereotype.Component;

@Component
public class SheetForm {
	Integer sheet_id;
	String sheet_name;
	Integer public_flag;

	public Integer getSheet_id() {
		return sheet_id;
	}

	public void setSheet_id(Integer sheet_id) {
		this.sheet_id = sheet_id;
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
