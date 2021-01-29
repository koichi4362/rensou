package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Users;

public class Dao {

	public void newAccount(Connection con, Users user) throws Exception {
		String newAccount_sql = "INSERT INTO users(user_name,e_mail,passwd) VALUES(?,?,?)";
		if (user.getUserName() != null
				|| user.getEmail() != null
				|| user.getPasswd() != null) {
			System.out.println("userName,email,passwdのいずれかが空欄");
			throw new RuntimeException();
		}
		try (PreparedStatement stmt = con.prepareStatement(newAccount_sql)) {
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPasswd());
			stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new RuntimeException(sqle);
		}
	}

	public Users login(Connection con, Users user) throws Exception {
		Users loginUser = new Users();
		String serch = "SELECT user_name, e_mail, passwd FROM users WHERE e_mail = ?";
		try (PreparedStatement stmt = con.prepareStatement(serch)) {
			stmt.setString(1, user.getEmail());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				loginUser.setUserName(rs.getString("user_name"));
				loginUser.setEmail(rs.getString("e_mail"));
				loginUser.setPasswd(rs.getString("passwd"));
			}
			if (user.getPasswd().equals(loginUser.getPasswd())) {
				return loginUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return null;
	}

	public void createNewSheet(Connection con, String userName, String newSheetName) {
	}

}
