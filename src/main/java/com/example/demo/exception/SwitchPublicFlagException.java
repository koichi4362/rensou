package com.example.demo.exception;

public class SwitchPublicFlagException extends RuntimeException {

	public SwitchPublicFlagException() {
		super("シートの更新に失敗しました。");
	}

	public SwitchPublicFlagException(String msg) {
		super(msg);
	}
}
