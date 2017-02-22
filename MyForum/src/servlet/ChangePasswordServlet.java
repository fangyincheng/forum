package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserTable;
import dao.UserTableDao;

/**
 * 修改用户密码
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class ChangePasswordServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String old_password = request.getParameter("old_password");
		String new_password = request.getParameter("new_password");
		//获取session对象并取出user
		HttpSession session = request.getSession();
		UserTable u = ((UserTable)(session.getAttribute("user")));
		//比较原密码是否相同并修改数据库，否则回到修改密码界面
		if(old_password.equals(u.getPassword())) {
			if(new UserTableDao().updateUserPassword(new_password, u.getId())) {
				//这一句将session中的user的密码改变
				u.setPassword(new_password);
				response.sendRedirect("user/change_sucess.jsp");
			} else {
				request.getRequestDispatcher("person_change_password.jsp").forward(request, response);
			}
		} else {
			request.getRequestDispatcher("person_change_password.jsp").forward(request, response);
		}
	}

}
