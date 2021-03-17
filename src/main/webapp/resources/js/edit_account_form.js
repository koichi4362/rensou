function CheckPassword(passwd) {
	let input1 = passwd.value;
	if (input.length > 0 && (input1.length < 4 || input1.length > 16)) {
		passwd.setCustomValidity("パスワードは4文字以上16文字以内で入力してください");
	} else {
		passwd.setCustomValidity('');
	}
}
