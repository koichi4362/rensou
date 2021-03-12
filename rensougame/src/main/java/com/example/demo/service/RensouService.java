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
@Transactional
public class RensouService {

	@Autowired
	private RensouDao rensouDao;

	/**
	 * 引数のUserFormをDAOに渡すメソッドです。
	 * DAOではUserFormの情報をもとに新規アカウントを作成します。
	 * @param userform
	 * @throws Exception
	 */
	public void doCreateAccount(UserForm userform) throws Exception {
		try {
			rensouDao.doCreateAccount(userform);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateAccountException();
		}
	}

	/**
	 * 引数のUserFormをDAOに渡すメソッドです。
	 * DAOでUserFormの情報をもとにログイン処理をし、ログインできたユーザーをUserインスタンスで返します。
	 * @param userform
	 * @return
	 * @throws Exception
	 */
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
	 * SheetのListをJSON文字列表現に変換するメソッドです。
	 * 引数のListのSheetはsheet_name,sheet_id,public_flagに値が入っている必要があります。
	 * @param sheetList
	 * @return	引数のSheetのListのJSON文字列表現
	 */
	public String createSheetsJData(List<Sheet> sheetList) {
		if (sheetList.size() == 0) {
			return null;
		}
		if ((sheetList.get(0).getUser_id()) == null) {
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
		} else if ((sheetList.get(0).getGood_count()) != null) {
			String json = "{\"sheet_list\":[";
			for (int i = 0; i < sheetList.size(); i++) {
				String user_name = rensouDao.getUserNameByUserId(sheetList.get(i).getUser_id());
				json = json + " { "
						+ " \"user_name\" : \"" + user_name
						+ "\" , \"sheet_name\" : \"" + sheetList.get(i).getSheet_name()
						+ "\" , \"sheet_id\" : " + sheetList.get(i).getSheet_id()
						+ " , \"public_flag\" : " + sheetList.get(i).getPublic_flag()
						+ " , \"good_count\" : " + sheetList.get(i).getGood_count()
						+ " } ";
				if (i != sheetList.size() - 1) {
					json = json + ",";
				}
			}
			json = json + "]}";
			return json;
		} else {
			String json = "{\"sheet_list\":[";
			for (int i = 0; i < sheetList.size(); i++) {
				String user_name = rensouDao.getUserNameByUserId(sheetList.get(i).getUser_id());
				json = json + " { "
						+ " \"user_name\" : \"" + user_name
						+ "\" , \"sheet_name\" : \"" + sheetList.get(i).getSheet_name()
						+ "\" , \"sheet_id\" : " + sheetList.get(i).getSheet_id()
						+ " , \"public_flag\" : " + sheetList.get(i).getPublic_flag()
						+ " } ";
				if (i != sheetList.size() - 1) {
					json = json + ",";
				}
			}
			json = json + "]}";
			return json;
		}
	}

	public String getMySheetsJData(User loginUser) {
		List<Sheet> sheetList = rensouDao.getSheetList(loginUser.getUser_id());
		return createSheetsJData(sheetList);
	}

	public String getPublicSheetsJData() {
		List<Sheet> sheetList = rensouDao.getPublicSheets();
		if (sheetList != null) {
			for (int i = 0; i < sheetList.size(); i++) {
				sheetList.get(i).setGood_count(countGood(sheetList.get(i).getSheet_id()));
			}
		}
		return createSheetsJData(sheetList);
	}

	public String gerStoppedSheetsJData() {
		List<Sheet> sheetList = rensouDao.getStoppedSheets();
		return createSheetsJData(sheetList);
	}

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

	public Sheet getSheetBySheetId(int sheet_id) {
		return rensouDao.getSheetBySheetId(sheet_id);
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

	public String getJsonPublicSheets(User loginUser) {
		List<Sheet> sheetList = rensouDao.getSheetList(loginUser.getUser_id());
		return createSheetsJData(sheetList);
	}

	public void kanri_doCreateAccount(UserForm userform) throws Exception {
		try {
			rensouDao.kanri_doCreateAccount(userform);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateAccountException();
		}
	}

	public boolean isManager(User loginUser) {
		try {
			if (loginUser.getUser_role() != 9) {
				System.out.println("管理者ではないユーザーが管理者専用ページにアクセスしました");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void stopOpenPublic(int sheet_id) {
		rensouDao.stopOpenPublic(sheet_id);
	}

	public void allowOpenPublic(int sheet_id) {
		rensouDao.allowOpenPublic(sheet_id);
	}

	public void addGood(int user_id, int sheet_id) {
		rensouDao.addGood(user_id, sheet_id);
	}

	public void cancelGood(int user_id, int sheet_id) {
		rensouDao.cancelGood(user_id, sheet_id);
	}

	public int countGood(int sheet_id) {
		List<Sheet> list = rensouDao.likedUsers(sheet_id);
		int good_count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUser_id() != null) {
				good_count++;
			}
		}
		return good_count;
	}

	public int checkGood(int user_id, int sheet_id) {
		List<Sheet> list = rensouDao.likedUsers(sheet_id);
		int good_flag = 0;
		try {
			if (list == null) {
				return 0;
			}
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getUser_id() == user_id) {
					good_flag = 1;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return good_flag;
	}

}
