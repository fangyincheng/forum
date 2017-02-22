package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Card;
import dao.CardDao;

/**
 * 调用DB类方法根据回复的帖子id查询指定帖子的回复帖子实现查看回复功能
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class LookReply extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从request获取帖子id作为回复帖子的回复id
		int replyId = Integer.parseInt(request.getParameter("replyId"));
		
		CardDao cd = new CardDao();
		HttpSession session = request.getSession();
		//根据帖子id查询原帖子并存入session
		Card c = cd.queryCardById(replyId);
		session.setAttribute("card", c);
		//根据帖子id作为回复id查询回复帖子并存入session
		ArrayList<Card> arr = cd.queryCard(replyId);
		session.setAttribute("reply_list", arr);
		
		response.sendRedirect("user/look_reply.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
