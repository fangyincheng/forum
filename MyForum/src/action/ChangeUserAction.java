package action;

import java.util.ArrayList;
import java.util.Map;


import beans.UserTable;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.UserTableDao;

@SuppressWarnings("serial")
public class ChangeUserAction extends ActionSupport {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		UserTableDao db = new UserTableDao();
//		HttpSession session = request.getSession();
		Map session = (Map) ActionContext.getContext().get("session");
		//查询所有用户
		ArrayList<UserTable> arr = db.queryUser();
		session.put("user_list", arr);
//		response.sendRedirect("user_manage/person_change_user.jsp");
		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
