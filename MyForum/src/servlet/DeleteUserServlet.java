package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserTableDao;


/**
 * 根据管理用户界面传来的用户id删除用户及其帖子
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class DeleteUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求页面获取用户id
		int id = Integer.parseInt(request.getParameter("id"));
		
		UserTableDao ud = new UserTableDao();
		
		if(ud.deleteUser(id)) {//调Dao类的方法删除版块和版块对应帖子
			response.sendRedirect("ChangeUserServlet");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
