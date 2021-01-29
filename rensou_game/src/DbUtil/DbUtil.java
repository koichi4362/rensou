package DbUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

	private static final String USER = "axizuser";
	private static final String PASSWORD = "axiz";
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String DB_NAME = "rensou";
	private static final String URL = "jdbc:postgresql://localhost/" + DB_NAME;

	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			System.out.println("データベース接続エラー");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
