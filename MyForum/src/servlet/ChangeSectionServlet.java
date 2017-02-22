package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Section;
import dao.SectionDao;

@SuppressWarnings("serial")
public class ChangeSectionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SectionDao sd = new SectionDao();
		HttpSession session = request.getSession();
		//获取数据库版块表内容并放进session
		ArrayList<Section> section_list = sd.querySection();
		session.setAttribute("section_list", section_list);
		
		response.sendRedirect("user_manage/person_change_section.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
