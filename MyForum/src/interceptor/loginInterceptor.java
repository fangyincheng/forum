package interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

@SuppressWarnings("serial")
public class loginInterceptor implements Interceptor{

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("拦截器已销毁！");
	}

	public void init() {
		// TODO Auto-generated method stub
		System.out.println("拦截器已加载！");
	}

	// 判断session对象中是否存在名为user的对象，如果存在说明登录过了，没有存在说明用户没有登录
	// 该过滤器的作用保证如果用户没有登录，就不能显示留言、添加留言
	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		Map session = (Map) ActionContext.getContext().get("session");
		if (session.get("user") != null) {//已登录
			String result = arg0.invoke();//检查是否还有拦截器，有的话继续调用余下的拦截器，没有则执行action的业务逻辑
			return result;
		} else {//没登录
			System.out.println("没有登录！！");
			return Action.ERROR;
		}
	}
}
