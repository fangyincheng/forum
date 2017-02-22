package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CardDao;
import dao.SectionDao;
import beans.Card;
import beans.Section;
import beans.UserTable;

@SuppressWarnings("serial")
public class SearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String key = request.getParameter("key");
		CardDao cd = new CardDao();
		SectionDao sd = new SectionDao();
		//查询帖子和版块信息
		ArrayList<Card> ac = cd.queryByKey(key);
		ArrayList<Section> as = sd.querySection();
		//根据版块id查询版主
		int host = sd.querySectionHost(-1);
		
		HttpSession session = request.getSession();
		//往session中传进ac,as
		session.setAttribute("card_list", ac);
		session.setAttribute("section_list", as);	
		//往session中传进sectionId和host
		session.setAttribute("sectionId", -1);
		session.setAttribute("host", host);
		
		UserTable u = (UserTable) request.getSession().getAttribute("user");
		System.out.print(u);
		//根据权限跳转主页
		if(u != null && u.getPower() == 1)
			response.sendRedirect("user_manage/main.jsp");
		else
			response.sendRedirect("user_common/main.jsp");
	}

}
