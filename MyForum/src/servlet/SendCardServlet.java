package servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Card;
import beans.UserTable;
import dao.CardDao;
import dao.SectionDao;
import dao.UserTableDao;

/**
 * 从发帖界面接收内容往数据库插入帖子实现发帖功能
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class SendCardServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int sectionId = Integer.parseInt(request.getParameter("select_section"));
		//从session中获取当前用户对象
		UserTable u = (UserTable) request.getSession().getAttribute("user");
		//建立帖子类对应数据库
		Card c = new Card();
		c.setUserTable(new UserTableDao().queryUser(u.getUsername()));
//		c.setNameId(u.getId());
		c.setName(u.getUsername());
		c.setContents(content);
		c.setDate(new Date(System.currentTimeMillis()));
		c.setTitle(title);
		c.setSection(new SectionDao().querySection(sectionId));
//		c.setSectionId(sectionId);
		c.setReplyId(0);
		
		if(new CardDao().insertCard(c)) {
			response.sendRedirect("user/send_success.jsp");
		} else {
			request.getRequestDispatcher("user/send_card.jsp").forward(request, response);
		}
	}

}
