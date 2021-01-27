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
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserForm;

@Repository
public class RensouDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	private String findUserByEmailSql = "SELECT * FROM users WHERE e_mail = :e_mail";
	private String findUserByIdSql = "SELECT * FROM users WHERE user_id = :user_id";

	public void doCreateAccount(UserForm userform) {
		String createAccountSql = "INSERT INTO users(user_name, e_mail, passwd, user_role) VALUES "
				+ "(:user_name, :e_mail, :passwd, 1)";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("user_name", userform.getUser_name());
		param.addValue("e_mail", userform.getE_mail());
		param.addValue("passwd", userform.getPasswd());
		jdbcTemplate.update(createAccountSql, param);
	}

	public User doLogin(UserForm userform) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("e_mail", userform.getE_mail());
		List<User> List = jdbcTemplate.query(findUserByEmailSql, param,
				new BeanPropertyRowMapper<User>(User.class));
		if (List.get(0).getPasswd() == userform.getPasswd()) {
			return null;
		}
		return List.get(0);
	}

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

	public User refleshLoginUser(User loginUser) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("user_id", loginUser.getUser_id());
		List<User> List = jdbcTemplate.query(findUserByIdSql, param, new BeanPropertyRowMapper<User>(User.class));
		return List.get(0);
	}

	/**
	 * シートを新しく作成し、作成したシートのシートIDをString型で返すメソッドです。
	 * @param rensouForm
	 * @return
	 */
	public Integer createSheet(RensouForm rensouForm) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String createSheetSql = "INSERT INTO sheets(user_id , sheet_name) VALUES( :user_id , :sheet_name )";
		param.addValue("user_id", rensouForm.getUser_id());
		param.addValue("sheet_name", rensouForm.getSheet_name());
		jdbcTemplate.update(createSheetSql, param);

		String findNewSheet = "SELECT MAX(sheet_id)AS sheet_id FROM sheets";
		List<Sheet> list = jdbcTemplate.query(findNewSheet, new BeanPropertyRowMapper<Sheet>(Sheet.class));
		return list.get(0).getSheet_id();
	}

	public void updateSheetName(RensouForm rensouForm) {
		String updateSheetName = "UPDATE sheets SET sheet_name = :sheet_name WHERE sheet_id = :sheet_id";
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("sheet_name", rensouForm.getSheet_name());
		param.addValue("sheet_id", rensouForm.getSheet_id());
		jdbcTemplate.update(updateSheetName, param);
	}

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

	public void updateNodes(List<Node> eNodeList) {
		String updateNodeSql = "UPDATE nodes SET node_name = :node_name WHERE node_id = :node_id ";
		for (Node node : eNodeList) {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("node_name", node.getNode_name());
			param.addValue("node_id", node.getSheet_id());
			jdbcTemplate.update(updateNodeSql, param);
		}
	}

	public List<Node> openSheet(Integer sheet_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String openSheetSql = "SELECT node_id,node_name FROM nodes WHERE sheet_id = :sheet_id ORDER BY node_id";
		param.addValue("sheet_id", sheet_id);
		List<Node> list = jdbcTemplate.query(openSheetSql, param, new BeanPropertyRowMapper<Node>(Node.class));
		return list;
	}

	public List<Sheet> getSheetList(int user_id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		String getSheetListSql = "SELECT sheet_id , user_id , sheet_name , public_flag FROM sheets WHERE user_id = :user_id ORDER BY created_at";
		param.addValue("user_id", user_id);
		return jdbcTemplate.query(getSheetListSql, param, new BeanPropertyRowMapper<Sheet>(Sheet.class));
	}

}