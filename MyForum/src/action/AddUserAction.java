package action;

import java.util.Map;

import beans.UserTable;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.UserTableDao;

@SuppressWarnings("serial")
public class AddUserAction extends ActionSupport {
	
	private String name;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		UserTableDao ud = new UserTableDao();
		//查询要添加的用户是否已存在
		UserTable user = ud.queryUser(name);
		Map session = (Map) ActionContext.getContext().get("session");
		if(name.equals("") || password.equals("")) {
			session.put("error_m", "请输入内容！");
		} else if(user == null) {
			UserTable u = new UserTable();
			u.setUsername(name);
			u.setPassword(password);
			u.setPower(0);
			ud.insertUser(u);
			session.put("error_m", "");
//			response.sendRedirect("ChangeUserServlet");
		} else {
			session.put("error_m", "用户名已存在！");
//			request.getRequestDispatcher("ChangeUserServlet").forward(request, response);
		}
		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
