package action;

import java.util.ArrayList;
import java.util.Map;


import beans.Card;
import beans.Section;
import beans.UserTable;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.CardDao;
import dao.SectionDao;

@SuppressWarnings("serial")
public class SearchAction extends ActionSupport {
	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		CardDao cd = new CardDao();
		SectionDao sd = new SectionDao();
		//查询帖子和版块信息
		ArrayList<Card> ac = cd.queryByKey(key);
		ArrayList<Section> as = sd.querySection();
		//根据版块id查询版主
		int host = sd.querySectionHost(-1);
		
		Map session = (Map) ActionContext.getContext().get("session");
//		HttpSession session = request.getSession();
		//往session中传进ac,as
		session.put("card_list", ac);
		session.put("section_list", as);	
		//往session中传进sectionId和host
		session.put("sectionId", -1);
		session.put("host", host);
		
		UserTable u = (UserTable)session.get("user");
		System.out.print(u);
		//根据权限跳转主页
		if(u != null && u.getPower() == 1)
//			response.sendRedirect("user_manage/main.jsp");
			return "success_manage";
		else
//			response.sendRedirect("user_common/main.jsp");
			return "success_common";
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
