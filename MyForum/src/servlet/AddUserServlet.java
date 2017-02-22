package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserTableDao;
import beans.UserTable;

/**
 * 从管理用户界面获取参数添加用户到数据库
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class AddUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		UserTableDao ud = new UserTableDao();
		//查询要添加的用户是否已存在
		UserTable user = ud.queryUser(name);
		if(user == null) {
			UserTable u = new UserTable();
			u.setUsername(name);
			u.setPassword(password);
			u.setPower(0);
			ud.insertUser(u);
			request.setAttribute("error_m", "");
			response.sendRedirect("ChangeUserServlet");
		} else {
			request.setAttribute("error_m", "用户名已存在！");
			request.getRequestDispatcher("ChangeUserServlet").forward(request, response);
		}
	}
	
}
