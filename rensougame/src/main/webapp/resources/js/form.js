
function CheckPassword(passwd) {
	let input1 = passwd.value;
	if (input1.length < 4 || input1.length > 16) {
		passwd.setCustomValidity("パスワードは4文字以上16文字以内で入力してください");
	}else {
		passwd.setCustomValidity('');
	}
}

function Check2ndPassword(passwd2) {
	// 入力値取得
	let input1 = passwd.value;
	let input2 = passwd2.value;
	// パスワード比較
	if (input1 != input2) {
		passwd2.setCustomValidity("入力値が一致しません。");
	} else {
		passwd2.setCustomValidity('');
	}
}
