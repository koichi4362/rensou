package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Node;
import com.example.demo.Entity.RensouForm;
import com.example.demo.Entity.Sheet;
import com.example.demo.Entity.SheetForm;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserForm;

@Repository
public class RensouDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 入力された値をもとにアカウントを作成するSQLを発行するメソッドです。
	 * @param userform
	 */
	public void doCreateAccount(UserForm userform) {
		String createAccountSql = "INSERT INTO users(user_name, e_mail, passwd, user_role) VALUES "
				+ "(:user_name, :e_mail, :passwd, 1)";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("user_name", userform.getUser_name());
		param.addValue("e_mail", userform.getE_mail());
		param.addValue("passwd", userform.getPasswd());
		jdbcTemplate.update(createAccountSql, param);
	}

	/**
	 * 入力された値をもとにログイン処理を行うメソッドです。
	 * @param userform
	 * @return ログインするユーザーのUserインスタンス
	 */
	public User doLogin(UserForm userform) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String findUserByEmailSql = "SELECT user_id,user_name,e_mail,passwd,user_role FROM users WHERE e_mail = :e_mail";
		param.addValue("e_mail", userform.getE_mail());
		List<User> List = jdbcTemplate.query(findUserByEmailSql, param,
				new BeanPropertyRowMapper<User>(User.class));
		if (!List.get(0).getPasswd().equals(userform.getPasswd())) {
			return null;
		}
		return List.get(0);
	}

	/**
	 * 入力された値をもとにアカウント情報を更新するメソッドです。
	 * @param form
	 * @param loginUser
	 */
	public void doEditAccount(UserForm form, User loginUser) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String editPart1 = " UPDATE users SET ";
		String editPart2 = " WHERE user_id = :user_id ";
		boolean commaFlag = false;
		if (!form.getUser_name().trim().isEmpty()) {
			editPart1 = editPart1.concat(" user_name = :user_name ");
			param.addValue("user_name", form.getUser_name());
			commaFlag = true;
		}
		if (!form.getE_mail().trim().isEmpty()) {
			if (commaFlag == true) {
				editPart1 = editPart1 + " , ";
			}
			editPart1 = editPart1.concat(" e_mail = :e_mail ");
			param.addValue("e_mail", form.getUser_name());
			commaFlag = true;
		}
		if (!form.getPasswd().trim().isEmpty()) {
			if (commaFlag == true) {
				editPart1 = editPart1 + " , ";
			}
			editPart1 = editPart1.concat(" passwd = :passwd ");
			param.addValue("passwd", form.getPasswd());
		}
		String editUserSql = editPart1 + editPart2;
		param.addValue("user_id", loginUser.getUser_id());
		jdbcTemplate.update(editUserSql, param);
	}

	/**
	 * ログインしているユーザーの情報を最新の状態に更新するメソッドです。
	 * @param loginUser
	 * @return ログインしているユーザーのUserインスタンス
	 */
	public User refleshLoginUser(User loginUser) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String findUserByIdSql = "SELECT user_id,user_name,e_mail,passwd,user_role FROM users WHERE user_id = :user_id";
		param.addValue("user_id", loginUser.getUser_id());
		List<User> List = jdbcTemplate.query(findUserByIdSql, param, new BeanPropertyRowMapper<User>(User.class));
		return List.get(0);
	}

	/**
	 * シートを新しく作成するメソッドです。
	 * @param rensouForm
	 * @return 新しく作成したシートのsheet_id
	 */
	public Integer createSheet(RensouForm rensouForm) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String createSheetSql = "INSERT INTO sheets(user_id , sheet_name) VALUES( :user_id , :sheet_name )";
		param.addValue("user_id", rensouForm.getUser_id());
		param.addValue("sheet_name", "新しいシート");
		jdbcTemplate.update(createSheetSql, param);

		String findNewSheet = "SELECT MAX(sheet_id)AS sheet_id FROM sheets";
		List<Sheet> list = jdbcTemplate.query(findNewSheet, new BeanPropertyRowMapper<Sheet>(Sheet.class));
		return list.get(0).getSheet_id();
	}

	/**
	 * 入力された値をもとにシート名を更新するメソッドです。
	 * @param rensouForm
	 */
	public void updateSheetName(RensouForm rensouForm) {
		String updateSheetName = "UPDATE sheets SET sheet_name = :sheet_name WHERE sheet_id = :sheet_id";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("sheet_name", rensouForm.getSheet_name());
		param.addValue("sheet_id", rensouForm.getSheet_id());
		jdbcTemplate.update(updateSheetName, param);
	}

	/**
	 * 同一シート内で最大値のnode_idを返すメソッドです。
	 * @param rensouForm
	 * @return シート内で最大値のnode_id
	 */
	public Integer findMaxIdInSheet(RensouForm rensouForm) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String findMaxIdInSheet = "SELECT MAX(node_id)AS node_id FROM nodes WHERE sheet_id = :sheet_id";
		param.addValue("sheet_id", rensouForm.getSheet_id());
		List<Node> list = jdbcTemplate.query(findMaxIdInSheet, param, new BeanPropertyRowMapper<Node>(Node.class));
		if (list == null) {
			return null;
		}
		return list.get(0).getNode_id();
	}

	/**
	 * シートに入力された新しいノードをDBに保存するメソッドです。
	 * @param nNodeList
	 */
	public void addNodes(List<Node> nNodeList) {
		String addNodeSql = "INSERT INTO nodes(node_name , sheet_id , parent_id) VALUES(:node_name , :sheet_id , :parent_id)";
		for (Node node : nNodeList) {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("node_name", node.getNode_name());
			param.addValue("sheet_id", node.getSheet_id());
			param.addValue("parent_id", node.getParent_id());
			jdbcTemplate.update(addNodeSql, param);
		}
	}

	/**
	 * シートに入力された状態に既存のノードを更新するメソッドです。
	 * @param eNodeList
	 */
	public void updateNodes(List<Node> eNodeList) {
		String updateNodeSql = "UPDATE nodes SET node_name = :node_name WHERE node_id = :node_id ";
		for (Node node : eNodeList) {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("node_name", node.getNode_name());
			param.addValue("node_id", node.getSheet_id());
			jdbcTemplate.update(updateNodeSql, param);
		}
	}

	/**
	 * sheet_idをもとにシートを展開するメソッドです。
	 * @param sheet_id
	 * @return 展開するシートのノードのList
	 */
	public List<Node> openSheet(Integer sheet_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String openSheetSql = "SELECT node_id,node_name FROM nodes WHERE sheet_id = :sheet_id ORDER BY node_id";
		param.addValue("sheet_id", sheet_id);
		List<Node> list = jdbcTemplate.query(openSheetSql, param, new BeanPropertyRowMapper<Node>(Node.class));
		return list;
	}

	/**
	 * sheet_idをもとにそのシートのsheet_nameを取得するメソッドです。
	 * @param sheet_id
	 * @return 引数のsheet_idのシートのsheet_name
	 */
	public Sheet getSheetBySheetId(int sheet_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String getSheetNameSql = "SELECT sheet_id, sheet_name, user_id, public_flag  FROM sheets WHERE sheet_id = :sheet_id";
		param.addValue("sheet_id", sheet_id);
		List<Sheet> list = jdbcTemplate.query(getSheetNameSql, param, new BeanPropertyRowMapper<Sheet>(Sheet.class));
		return list.get(0);
	}

	/**
	 * マイページに一覧表示されるシートを、ログインしているユーザーのuser_idをもとに取得するメソッドです。
	 * @param user_id
	 * @return 引数のuser_idのユーザーに紐づいたSheetのList
	 */
	public List<Sheet> getSheetList(int user_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String getSheetListSql = "SELECT sheet_id , user_id , sheet_name , public_flag FROM sheets WHERE user_id = :user_id ORDER BY created_at";
		param.addValue("user_id", user_id);
		return jdbcTemplate.query(getSheetListSql, param, new BeanPropertyRowMapper<Sheet>(Sheet.class));
	}

	/**
	 * 入力された値をもとにシートのsheet_nameを更新するメソッドです。
	 * @param sheetForm
	 */
	public void updateSheetName(SheetForm sheetForm) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String updateSheetNameSql = "UPDATE sheets SET sheet_name = :sheet_name WHERE sheet_id = :sheet_id ";
		param.addValue("sheet_name", sheetForm.getSheet_name());
		param.addValue("sheet_id", sheetForm.getSheet_id());
		jdbcTemplate.update(updateSheetNameSql, param);
	}

	/**
	 * sheet_idをもとにシートのpublic_flagを返すメソッドです。
	 * public_flagは 0...非公開, 1...公開, 9...公開不可です。
	 * @param sheet_id
	 * @return 引数のsheet_idのシートのpublic_flag
	 */
	public Integer getPublicFlag(int sheet_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String getPublicFlagSql = "SELECT public_flag FROM sheets WHERE sheet_id = :sheet_id";
		param.addValue("sheet_id", sheet_id);
		List<Sheet> list = jdbcTemplate.query(getPublicFlagSql, param, new BeanPropertyRowMapper<Sheet>(Sheet.class));
		return list.get(0).getPublic_flag();
	}

	/**
	 * シートのpublic_flagを指定された値に更新するメソッドです。
	 * @param sheet_id
	 * @param public_flag
	 */
	public void switchPublicFlag(int sheet_id, int public_flag) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String switchPublicFlagSql = "UPDATE sheets SET public_flag = :public_flag WHERE sheet_id = :sheet_id";
		param.addValue("public_flag", public_flag);
		param.addValue("sheet_id", sheet_id);
		jdbcTemplate.update(switchPublicFlagSql, param);
	}

	/**
	 * user_idをもとにそのユーザーのuser_nameを取得するメソッドです。
	 * @param user_id
	 * @return
	 */
	public String getUserNameByUserId(int user_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String getUserNameSql = "SELECT user_name FROM users WHERE user_id = :user_id";
		param.addValue("user_id", user_id);
		List<User> list = jdbcTemplate.query(getUserNameSql, param, new BeanPropertyRowMapper<User>(User.class));
		return list.get(0).getUser_name();
	}

	/**
	 * すべてのシートからpublic_flagが1(公開)のシートを抽出して返すメソッドです。
	 * @return 公開中のSheetのList
	 */
	public List<Sheet> getPublicSheets() {
		String getPublicSheetsSql = "SELECT sheet_id , user_id , sheet_name FROM sheets WHERE public_flag = 1 ORDER BY public_date";
		List<Sheet> list = jdbcTemplate.query(getPublicSheetsSql, new BeanPropertyRowMapper<Sheet>(Sheet.class));
		return list;
	}

	/**
	 * すべてのシートからpublic_flagが9(公開禁止)のシートを抽出して返すメソッドです。
	 * @return 公開禁止中のSheetのList
	 */
	public List<Sheet> getStoppedSheets() {
		String getStoppedSheetsSql = "SELECT sheet_id , user_id , sheet_name FROM sheets WHERE public_flag = 9 ORDER BY public_date";
		List<Sheet> list = jdbcTemplate.query(getStoppedSheetsSql, new BeanPropertyRowMapper<Sheet>(Sheet.class));
		return list;
	}

	/**
	 * sheet_idをもとにシートを公開禁止にするメソッドです。
	 * @param sheet_id
	 */
	public void stopOpenPublic(int sheet_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String stopOpenPublicSql = "UPDATE sheets SET public_flag = 9 WHERE sheet_id = :sheet_id";
		param.addValue("sheet_id", sheet_id);
		jdbcTemplate.update(stopOpenPublicSql, param);
	}

	/**
	 * sheet_idをもとにシートを公開可能（非公開）にするメソッドです。
	 * @param sheet_id
	 */
	public void allowOpenPublic(int sheet_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String allowOpenPublicSql = "UPDATE sheets SET public_flag = 0 WHERE sheet_id = :sheet_id";
		param.addValue("sheet_id", sheet_id);
		jdbcTemplate.update(allowOpenPublicSql, param);
	}

	public void kanri_doCreateAccount(UserForm userform) {
		String createAccountSql = "INSERT INTO users(user_name, e_mail, passwd, user_role) VALUES "
				+ "(:user_name, :e_mail, :passwd, 9)";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("user_name", userform.getUser_name());
		param.addValue("e_mail", userform.getE_mail());
		param.addValue("passwd", userform.getPasswd());
		jdbcTemplate.update(createAccountSql, param);
	}

	public void addGood(int user_id, int sheet_id) {
		String addGoodSql = "INSERT INTO good_sheets(user_id,sheet_id) VALUES(:user_id , :sheet_id)";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("user_id", user_id);
		param.addValue("sheet_id", sheet_id);
		jdbcTemplate.update(addGoodSql, param);
	}

	public void cancelGood(int user_id, int sheet_id) {
		String cancelGoodSql = "DELETE FROM good_sheets WHERE user_id = :user_id AND sheet_id = :sheet_id";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("user_id", user_id);
		param.addValue("sheet_id", sheet_id);
		jdbcTemplate.update(cancelGoodSql, param);
	}

	public List<Sheet> likedUsers(int sheet_id) {
		try {
			String countGoodSql = "SELECT user_id FROM good_sheets WHERE sheet_id = :sheet_id";
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("sheet_id", sheet_id);
			List<Sheet> list = jdbcTemplate.query(countGoodSql, param,
					new BeanPropertyRowMapper<Sheet>(Sheet.class));
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
