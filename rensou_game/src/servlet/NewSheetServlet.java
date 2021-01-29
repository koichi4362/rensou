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
 * Servlet implementation class NewSheet
 */
@WebServlet("/newSheet")
public class NewSheetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewSheetServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		String userName = null;
		String newSheetName = null;

		if (session.getAttribute("loginAccount") != null) {
			userName = ((Users) session.getAttribute("loginAccount")).getUserName();
		} else {
			request.setAttribute("msg", "シートを作成するにはアカウントにログインしてください");
			request.getRequestDispatcher("rensou_select.jsp").forward(request, response);
		}
		if (!request.getParameter("newSheetName").isEmpty()) {
			newSheetName = request.getParameter("newSheetName");
		} else {
			request.setAttribute("msg", "新しいシートの名前を入力してください");
			request.getRequestDispatcher("rensou_select.jsp").forward(request, response);
		}
		try (Connection con = DbUtil.getConnection()) {
			Dao dao = new Dao();
			dao.createNewSheet(con, userName, newSheetName);
			request.setAttribute("msg", "シートを作成しました");
		} catch (Exception e) {//何らかの不明なエラー
			e.printStackTrace();
			request.setAttribute("msg", "シートの作成に失敗しました。");
		}
		request.getRequestDispatcher("rensou_select.jsp").forward(request, response);

	}

}
