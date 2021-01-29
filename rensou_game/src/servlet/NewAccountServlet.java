package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DbUtil.DbUtil;
import dao.Dao;
import entity.Users;

/**
 * Servlet implementation class NewAccount
 */
@WebServlet("/newaccount")
public class NewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(true);

		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");
		try (Connection con = DbUtil.getConnection()) {
			Users user = new Users(userName, email, passwd);
			Dao dao = new Dao();
			dao.newAccount(con, user);
			session.setAttribute("loginAccount", user);
			request.getRequestDispatcher("rensou_create_success.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "登録に失敗しました。<br>入力内容を確認してもう一度お試しください。");
			request.getRequestDispatcher("rensou_newaccount.jsp").forward(request, response);
		}
	}

}
