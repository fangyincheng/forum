package action;

import java.util.Map;


import beans.Section;
import beans.UserTable;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.SectionDao;
import dao.UserTableDao;

@SuppressWarnings("serial")
public class UpdateSectionAction extends ActionSupport {
	
	private String name;
	private String hostName;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		SectionDao sd = new SectionDao();
		UserTableDao ud = new UserTableDao();
		
//		HttpSession session = request.getSession();
		Map session = (Map) ActionContext.getContext().get("session");
		
		Section s = sd.querySection(name);
		
		if(name.equals("")) {
			session.put("No_user", "请输入板块名！");
		} else if(hostName.equals("")) {//如果版主名为""则设置版主为0
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
						
		} else {
			//根据用户名查询用户，判断用户是否存在，若不存在则将请求转发给ChangeSectionServlet并提示错误
			UserTable user = ud.queryUser(hostName);
			if(user != null && s == null) {
				//创建section对象并插入数据库
				Section section = new Section();
				section.setName(name);
				section.setHost(user.getId());
				sd.insertSection(section);
				
			} else if(user == null) {
				session.put("No_user", "不存在该用户！");
			} else {
				//修改版主
				sd.updateSection(s.getId(),user.getId());
			}
		}
//		response.sendRedirect("ChangeSectionServlet");
		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}

}
