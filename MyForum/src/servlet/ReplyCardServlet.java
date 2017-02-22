package servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CardDao;
import dao.SectionDao;
import dao.UserTableDao;
import beans.Card;
import beans.UserTable;

/**
 * 调用DB类方法往数据库插入指定帖子的回复帖子
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class ReplyCardServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String reply = request.getParameter("reply");
		String replyId = request.getParameter("replyId");
		int sectionId = Integer.parseInt(request.getParameter("sectionId"));
		//从session中获取当前用户对象
		UserTable u = (UserTable) request.getSession().getAttribute("user");
		//建立帖子类对应数据库
		Card c = new Card();
		c.setUserTable(new UserTableDao().queryUser(u.getUsername()));
//		c.setNameId(u.getId());
		c.setName(u.getUsername());
		c.setContents(reply);
		c.setDate(new Date(System.currentTimeMillis()));
		c.setTitle("回复");
		c.setSection(new SectionDao().querySection(sectionId));
//		c.setSectionId(sectionId);
		c.setReplyId(Integer.parseInt(replyId));
		
		if(new CardDao().insertCard(c)) {
			response.sendRedirect("user/send_success.jsp");
		} else {
			request.getRequestDispatcher("user/reply.jsp").forward(request, response);
		}
	}

}
