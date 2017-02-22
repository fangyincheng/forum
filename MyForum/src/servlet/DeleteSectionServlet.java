package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SectionDao;


/**
 * 根据从界面获取的版块id实现管理员的删除版块功能（包括版块的帖子）
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class DeleteSectionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从请求页面获取版块id
		int id = Integer.parseInt(request.getParameter("id"));
		
		SectionDao sd = new SectionDao();
		
		if(sd.deleteSection(id)) {//调DB类的方法删除版块和版块对应帖子
			response.sendRedirect("ChangeSectionServlet");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
