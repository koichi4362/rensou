package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Node;
import com.example.demo.Entity.RensouForm;
import com.example.demo.Entity.Sheet;
import com.example.demo.Entity.SheetForm;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserForm;
import com.example.demo.dao.RensouDao;
import com.example.demo.exception.CreateAccountException;
import com.example.demo.exception.EditAccountException;
import com.example.demo.exception.LoginException;
import com.example.demo.exception.SwitchPublicFlagException;

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

	public String getJsonMySheets(User loginUser) {
		List<Sheet> sheetList = rensouDao.getSheetList(loginUser.getUser_id());
		return createJsonSheetsData(sheetList);
	}

	public String createJsonSheetsData(List<Sheet> sheetList) {
		String json = "{\"sheet_list\":[";
		for (int i = 0; i < sheetList.size(); i++) {
			json = json + " { "
					+ " \"sheet_name\" : \"" + sheetList.get(i).getSheet_name()
					+ "\" , \"sheet_id\" : \"" + sheetList.get(i).getSheet_id()
					+ "\" , \"public_flag\" : " + sheetList.get(i).getPublic_flag()
					+ " } ";
			if (i != sheetList.size() - 1) {
				json = json + ",";
			}
		}
		json = json + "]}";
		return json;
	}

	/**
	 * シートを新規作成/保存するメソッドです。
	 * 既存のシートの場合は保存のみを行います。
	 * @param rensouForm
	 */
	public void saveSheet(RensouForm rensouForm) throws Exception {
		if (rensouForm.getSheet_id() == 0) {
			rensouForm.setSheet_id(rensouDao.createSheet(rensouForm));
		}
		if (rensouForm.getnWordList() != null) {
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
	//
	//	public void updateSheetName(RensouForm rensouForm) throws Exception {
	//		try {
	//			rensouDao.updateSheetName(rensouForm);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			throw new RuntimeException();
	//		}
	//	}

	public String createJsonNodesData(int sheet_id) {
		List<Node> eNodeList = rensouDao.openSheet(sheet_id);
		String json = "{\"node_list\":[";
		for (int i = 0; i < eNodeList.size(); i++) {
			json = json + " { "
					+ " \"node_name\" : \"" + eNodeList.get(i).getNode_name()
					+ "\" , \"node_id\" : \"" + eNodeList.get(i).getNode_id() +
					"\" } ";
			if (i != eNodeList.size() - 1) {
				json = json + ",";
			}
		}
		json = json + "]}";
		return json;
	}

	public String getSheetNameFormSheetId(int sheet_id) {
		return rensouDao.getSheetNameFormSheetId(sheet_id);
	}

	public List<Sheet> getSheetList(User loginUser) throws Exception {
		if (loginUser.getUser_id() == null) {
			throw new LoginException();
		}
		return rensouDao.getSheetList(loginUser.getUser_id());
	}

	public void updateSheetName(SheetForm sheetForm) {
		rensouDao.updateSheetName(sheetForm);
	}

	public SheetForm switchPublicFlag(SheetForm sheetForm) throws Exception {
		sheetForm.setPublic_flag(rensouDao.getPublicFlag(sheetForm.getSheet_id()));
		if (sheetForm.getPublic_flag() == 0) {
			sheetForm.setPublic_flag(1);
		} else if (sheetForm.getPublic_flag() == 1) {
			sheetForm.setPublic_flag(0);
		} else {
			throw new SwitchPublicFlagException("このシートは公開できません。");
		}
		rensouDao.switchPublicFlag(sheetForm.getSheet_id(), sheetForm.getPublic_flag());
		return sheetForm;
	}

	public String getJsonPublicSheets() {
		List<Sheet> sheetList = rensouDao.getPublicSheets();
		String json = "{\"sheet_list\":[";
		for (int i = 0; i < sheetList.size(); i++) {
			String user_name = rensouDao.getUserNameByUserId(sheetList.get(i).getUser_id());
			json = json + " { "
					+ " \"user_name\" : \"" + user_name
					+ "\" , \"sheet_name\" : \"" + sheetList.get(i).getSheet_name()
					+ "\" , \"sheet_id\" : " + sheetList.get(i).getSheet_id()
					+ " } ";
			if (i != sheetList.size() - 1) {
				json = json + ",";
			}
		}
		json = json + "]}";
		return json;
	}

}
