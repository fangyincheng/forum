package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CardDao;
import beans.Card;
import beans.UserTable;

/**
 * 根据用户id和版块id从数据库获取帖子实现查看帖子功能（版块id为0是全部）
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class SelfCardServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int sectionId = Integer.parseInt(request.getParameter("sectionId"));
		//从session获取用户id
		HttpSession session = request.getSession();
		int nameId = ((UserTable)session.getAttribute("user")).getId();
		
		CardDao cd = new CardDao();
		//版块id查询帖子
		if(sectionId == -1) {
			//查询所有帖子
			ArrayList<Card> arr = cd.querySelfCard(nameId);
			//往session中传进arr
			session.setAttribute("self_card_list", arr);
			//往session中传进sectionId
			session.setAttribute("sectionId", sectionId);
			
			response.sendRedirect("user/self_message.jsp");
		} else {
			//根据版块id查询帖子
			ArrayList<Card> arr = cd.querySection_SelfCard(nameId, sectionId);
			//往session中传进arr
			session.setAttribute("self_card_list", arr);
			//往session中传进sectionId
			session.setAttribute("sectionId", sectionId);
			
			response.sendRedirect("user/self_message.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
