
package com.example.demo.Entity;

public class User {

	private String user_id;
	private String user_name;
	private String e_mail;
	private String passwd;
	private String user_role;
	private String token;

	public User() {
	}

	public User(Integer userId) {

	}

	public Integer getUser_id() {
		return Integer.parseInt(user_id);
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Integer getUser_role() {
		return Integer.parseInt(user_role);
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
