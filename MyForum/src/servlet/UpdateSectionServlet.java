package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SectionDao;
import dao.UserTableDao;
import beans.Section;
import beans.UserTable;

/**
 * 更新版块数据表，若没有该版块则增加插入该版块
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class UpdateSectionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求中获取参数
		String name = request.getParameter("name");
		String hostName = request.getParameter("hostName");
		
		SectionDao sd = new SectionDao();
		UserTableDao ud = new UserTableDao();
		
		HttpSession session = request.getSession();
		
		Section s = sd.querySection(name);
		
		//如果版主名为""则设置版主为0
		if(hostName == "") {
			if(s == null) {
				//创建section对象并插入数据库
				Section section = new Section();
				section.setName(name);
				section.setHost(0);
				sd.insertSection(section);
			} else {
				//修改版主为空
				sd.updateSection(s.getId(),0);
			}
			
			session.setAttribute("No_user", null);
			
		} else {
			//根据用户名查询用户，判断用户是否存在，若不存在则将请求转发给ChangeSectionServlet并提示错误
			UserTable user = ud.queryUser(hostName);
			if(user != null && s == null) {
				//创建section对象并插入数据库
				Section section = new Section();
				section.setName(name);
				section.setHost(user.getId());
				sd.insertSection(section);
				
				session.setAttribute("No_user", null);
			} else if(user == null) {
				session.setAttribute("No_user", "不存在该用户！");
			} else {
				//修改版主
				sd.updateSection(s.getId(),user.getId());
			}
		}
		response.sendRedirect("ChangeSectionServlet");
	}

}
