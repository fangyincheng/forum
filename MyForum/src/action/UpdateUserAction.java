package action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import dao.UserTableDao;

@SuppressWarnings("serial")
public class UpdateUserAction extends ActionSupport {
	
	private String power;
	private String id;

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		//从请求页面获取参数
		int id_ = Integer.parseInt(id);
		int power_ = Integer.parseInt(power);
		
		UserTableDao ud = new UserTableDao();
		ud.updateUserPower(power_, id_);
//		response.sendRedirect("ChangeUserServlet");
		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
