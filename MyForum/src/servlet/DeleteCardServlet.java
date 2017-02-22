package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CardDao;


/**
 * 调用DB类方法删除指定帖子与其回复帖子实现删除功能
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class DeleteCardServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		CardDao cd = new CardDao();
		if(cd.deleteCard(id)) {//删除帖子和本帖子的回复
			response.sendRedirect(request.getParameter("path") + "?sectionId=" + request.getParameter("sectionId"));
		}
	}
	 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
