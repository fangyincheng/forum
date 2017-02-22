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
 * 从登录jsp页面接收参数调用DB类的方法实现登录功能
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String username = request.getParameter("username");
		String userpassword = request.getParameter("userpassword");
		
		UserTableDao ud = new UserTableDao();
		
		if(username=="" || userpassword=="")
			request.setAttribute("error_massage", "请填写完整!");
		else if(ud.queryUser(username) != null) {
			//从数据库获取用户信息
			UserTable u = ud.queryUser(username, userpassword);
			if(u != null) {
				//向session对象传进u
				HttpSession session = request.getSession();
				session.setAttribute("user", u);
				
				response.sendRedirect("MainServlet?sectionId=-1");
				return;
				
			} else {
				request.setAttribute("error_password", "用户密码错误！");
			}
		} else {
			request.setAttribute("error_username", "用户名不存在!");
		}
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
