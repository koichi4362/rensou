package com.example.demo.exception;

public class EditAccountException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EditAccountException() {
		super("アカウント情報の更新が正常に完了しませんでした");
	}

	public EditAccountException(String msg) {
		super(msg);
	}
}
