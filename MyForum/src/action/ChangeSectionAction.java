package action;

import java.util.ArrayList;
import java.util.Map;


import beans.Section;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.SectionDao;

@SuppressWarnings("serial")
public class ChangeSectionAction extends ActionSupport {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		SectionDao sd = new SectionDao();
//		HttpSession session = request.getSession();
		Map session = (Map) ActionContext.getContext().get("session");
		//获取数据库版块表内容并放进session
		ArrayList<Section> section_list = sd.querySection();
		session.put("section_list", section_list);
		
//		response.sendRedirect("user_manage/person_change_section.jsp");
		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
