package com.example.demo.exception;

public class CreateAccountException extends Exception {
	private static final long serialVersionUID = 1L;

	public CreateAccountException() {
		super("アカウントの登録が正常に完了しませんでした");
	}

	public CreateAccountException(String msg) {
		super(msg);
	}
}
