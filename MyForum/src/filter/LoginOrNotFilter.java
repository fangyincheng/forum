package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 检查是否已登录的过滤器
 * @author 方银城
 *
 */
public class LoginOrNotFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//获取请求路径
		String request_uri = ((HttpServletRequest) request).getRequestURI();
		if (((HttpServletRequest)request).getSession().getAttribute("user") != null || 
				request_uri.equals("/MyForum/user/look_reply.jsp") || request_uri.equals("/MyForum/LookReply") ||
				request_uri.equals("/MyForum/login.jsp") || request_uri.equals("/MyForum/LoginServlet") ||
				request_uri.equals("/MyForum/register.jsp") || request_uri.equals("/MyForum/RegisterServlet") ||
				request_uri.equals("/MyForum/MainServlet") || request_uri.equals("/MyForum/user_common/main.jsp")) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse)response).sendRedirect("http://localhost:8080/MyForum/MainServlet?sectionId=-1");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
