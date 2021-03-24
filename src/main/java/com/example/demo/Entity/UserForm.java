package com.example.demo.Entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class UserForm {
	@NotBlank(message = "名前は必須です")
	private String user_name;

	@Email(message = "メールアドレスは必須です")
	private String e_mail;

	@NotBlank(message = "パスワードは必須です")
	private String passwd;

	private String token;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name.trim();
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail.trim();
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd.trim();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
