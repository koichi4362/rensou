package entity;

public class Users {

	private Integer userId;
	private String userName;
	private String email;
	private String passwd;

	public Users() {
	}

	public Users(String email, String passwd) {
		this.email = email;
		this.passwd = passwd;
	}

	public Users(String userName, String email, String passwd) {
		this.userName = userName;
		this.email = email;
		this.passwd = passwd;
	}

	public Users(Integer userId, String userName, String email, String passwd) {
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.passwd = passwd;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
