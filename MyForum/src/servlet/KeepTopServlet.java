package servlet;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CardDao;


/**
 * 实现置顶功能
 * @author 方银城
 *
 */
@SuppressWarnings("serial")
public class KeepTopServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取发贴人id
		int id = Integer.parseInt(request.getParameter("id"));
		
		CardDao cd = new CardDao();
		cd.updateTop(id, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
		response.sendRedirect("MainServlet?sectionId=" + request.getParameter("sectionId"));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}
