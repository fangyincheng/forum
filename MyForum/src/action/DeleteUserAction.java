package action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import dao.UserTableDao;

@SuppressWarnings("serial")
public class DeleteUserAction extends ActionSupport {
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//从请求页面获取用户id
		int id_ = Integer.parseInt(id);
		
		UserTableDao ud = new UserTableDao();
		
		if(ud.deleteUser(id_)) {//调Dao类的方法删除版块和版块对应帖子
//			response.sendRedirect("ChangeUserServlet");
			return Action.SUCCESS;
		}
		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
