package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Node;
import com.example.demo.Entity.RensouForm;
import com.example.demo.Entity.Sheet;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserForm;
import com.example.demo.dao.RensouDao;
import com.example.demo.exception.CreateAccountException;
import com.example.demo.exception.EditAccountException;
import com.example.demo.exception.LoginException;

@Service
public class RensouService {

	@Autowired
	private RensouDao rensouDao;

	public void doCreateAccount(UserForm userform) throws Exception {
		try {
			rensouDao.doCreateAccount(userform);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateAccountException();
		}
	}

	public User doLogin(UserForm userform) throws Exception {
		try {
			return rensouDao.doLogin(userform);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LoginException();
		}
	}

	public void doEditAccount(UserForm form, User loginUser) {
		try {
			rensouDao.doEditAccount(form, loginUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EditAccountException();
		}
	}

	public User refreshLoginUser(User loginUser) {
		return rensouDao.refleshLoginUser(loginUser);
	}

	/**
	 * シートを新規作成/保存するメソッドです。
	 * 既存のシートの場合は保存のみを行います。
	 * @param rensouForm
	 */
	public void saveSheet(RensouForm rensouForm) throws Exception {
		if (rensouForm.getnWordList() != null) {
			updateSheetName(rensouForm);
			List<Node> nNodeList = new ArrayList<>();
			for (int i = 0; i < rensouForm.getnWordList().size(); i++) {
				if (i == 0) {
					nNodeList.add(new Node(rensouForm.getnWordList().get(i).getWord(),
							rensouForm.getSheet_id(), rensouDao.findMaxIdInSheet(rensouForm)));
				} else {
					nNodeList.add(new Node(rensouForm.getnWordList().get(i).getWord(),
							rensouForm.getSheet_id(), 0));
				}
			}
			rensouDao.addNodes(nNodeList);
		}
		if (rensouForm.geteWordList() != null) {
			List<Node> eNodeList = new ArrayList<>();
			for (int i = 0; i < rensouForm.geteWordList().size(); i++) {
				if (rensouForm.geteWordList().get(i).getWord() != null
						&& !rensouForm.geteWordList().get(i).getWord().isEmpty()) {
					eNodeList.add(new Node(rensouForm.geteWordList().get(i).getWord(), i));
				}
			}
			rensouDao.updateNodes(eNodeList);
		}
	}

	public void updateSheetName(RensouForm rensouForm) throws Exception {
		try {
			if (rensouForm.getSheet_id() == null) {
				rensouForm.setSheet_name("新しいシート");
				rensouForm.setSheet_id(rensouDao.createSheet(rensouForm));
			} else {
				rensouDao.updateSheetName(rensouForm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public List<Node> openSheet(int sheet_id) {
		return rensouDao.openSheet(sheet_id);
	}

	public List<Sheet> getSheetList(User loginUser) throws Exception {
		if (loginUser.getUser_id() == null) {
			throw new LoginException();
		}
		return rensouDao.getSheetList(Integer.parseInt(loginUser.getUser_id()));
	}

}
