package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserTableDao;


/**
 * 设置用户为管理员或者不为管理员
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class UpdateUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求页面获取参数
		int id = Integer.parseInt(request.getParameter("id"));
		int power = Integer.parseInt(request.getParameter("power"));
		
		UserTableDao ud = new UserTableDao();
		ud.updateUserPower(power, id);
		response.sendRedirect("ChangeUserServlet");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
