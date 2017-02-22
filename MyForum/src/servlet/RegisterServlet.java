package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserTableDao;
import beans.UserTable;

/**
 * 从注册jsp页面接收参数调用DB类的方法实现注册功能
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String username = request.getParameter("username");
		String userpassword = request.getParameter("userpassword");
		String userpassword2 = request.getParameter("userpassword2");
		//建立用户类对应数据库
		UserTable u = new UserTable();
		u.setUsername(username);
		u.setPassword(userpassword);
		u.setPower(0);
		
		UserTableDao ud = new UserTableDao();
		
		if(username=="" || userpassword=="" || userpassword2=="")
			request.setAttribute("error_message", "请填写完整！");
		else if(!userpassword.equals(userpassword2))
			request.setAttribute("error_message", "请输入相同密码！");
		else if(ud.queryUser(username)==null && ud.insertUser(u)) {
			response.sendRedirect("login.jsp");
			return;
		} else{
			request.setAttribute("error_message", "注册失败，因为用户已存在！");
		}
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

}
