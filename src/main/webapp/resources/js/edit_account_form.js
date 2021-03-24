function CheckPassword(passwd) {
	let input = passwd.value.trim();
	if (input.length > 0 && (input.length < 4 || input.length > 16)) {
		passwd.setCustomValidity("パスワードは4文字以上16文字以内で入力してください");
	} else {
		passwd.setCustomValidity('');
	}
}

function CheckEmail(email) {
	let input = email.value.trim();
	if (input.length > 0 && input.indexOf('@') === -1) {
		email.setCustomValidity("この欄にはEメールアドレスを入力してください");
	} else {
		email.setCustomValidity('');
	}
}