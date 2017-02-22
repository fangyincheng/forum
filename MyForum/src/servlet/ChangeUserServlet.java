package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserTable;
import dao.UserTableDao;

/**
 * 查询所有用户信息跳转到管理员管理用户界面
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class ChangeUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserTableDao db = new UserTableDao();
		HttpSession session = request.getSession();
		//查询所有用户
		ArrayList<UserTable> arr = db.queryUser();
		session.setAttribute("user_list", arr);
		response.sendRedirect("user_manage/person_change_user.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
