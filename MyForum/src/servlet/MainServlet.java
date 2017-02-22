package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Card;
import beans.Section;
import beans.UserTable;
import dao.CardDao;
import dao.SectionDao;

/**
 * 根据版块id从数据库获取帖子实现查看帖子功能（0是全部）
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class MainServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SectionDao sd = new SectionDao();
		CardDao cd = new CardDao();
		int sectionId = Integer.parseInt(request.getParameter("sectionId"));
		//根据版块id查询版主
		int host = sd.querySectionHost(sectionId);
		//根据版块id查询帖子
		if(sectionId == -1) {
			//查询所有帖子
			ArrayList<Card> ac = cd.queryCard(0);
			//查询所有版块名字
			ArrayList<Section> as = sd.querySection();
			//往session中传进ac,as
			HttpSession session = request.getSession();
			session.setAttribute("card_list", ac);
			session.setAttribute("section_list", as);		
			//往session中传进sectionId和host
			session.setAttribute("sectionId", sectionId);
			session.setAttribute("host", host);
		} else {
			//根据版块id查询帖子
			ArrayList<Card> ac = cd.querySectionCard(sectionId);
			//往session中传进ac,as
			HttpSession session = request.getSession();
			session.setAttribute("card_list", ac);
			//往session中传进sectionId和host
			session.setAttribute("sectionId", sectionId);
			session.setAttribute("host", host);
		}
		UserTable u = (UserTable) request.getSession().getAttribute("user");
		//根据权限跳转主页
		if(u != null && u.getPower() == 1)
			response.sendRedirect("user_manage/main.jsp");
		else
			response.sendRedirect("user_common/main.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
