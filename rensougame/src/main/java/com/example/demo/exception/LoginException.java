package com.example.demo.exception;

public class LoginException extends Exception {

	private static final long serialVersionUID = 1L;

	public LoginException() {
		super("ログインが正常に完了しませんでした");
	}

	public LoginException(String msg) {
		super(msg);
	}
}
