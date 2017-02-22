package action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import dao.SectionDao;

@SuppressWarnings("serial")
public class DeleteSectionAction extends ActionSupport {
	
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
		//从请求页面获取版块id
		int id_ = Integer.parseInt(id);
		
		SectionDao sd = new SectionDao();
		
		if(sd.deleteSection(id_)) {//调DB类的方法删除版块和版块对应帖子
//			response.sendRedirect("ChangeSectionServlet");
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
