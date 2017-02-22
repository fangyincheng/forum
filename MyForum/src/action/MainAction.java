package action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import beans.Card;
import beans.Section;
import beans.UserTable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.CardDao;
import dao.SectionDao;

@SuppressWarnings("serial")
public class MainAction extends ActionSupport {
	
	private String sectionId;
	private String sec;
	
	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public void write() throws Exception {
		// TODO Auto-generated method stub
		
		SectionDao sd = new SectionDao();
		CardDao cd = new CardDao();
		
		HttpServletRequest request = ServletActionContext.getRequest();;
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/html;charset=UTF-8");
		InputStream inputStream = request.getInputStream();
//		byte[] b = new BufferedReader(new InputStreamReader(inputStream)).readLine().getBytes();
		byte[] b = IOUtils.toByteArray(inputStream);
		String str = new String(b,"UTF-8");
		str = str.substring(str.indexOf("{"),str.lastIndexOf("}")+1);
		JSONObject j = (JSONObject) JSONObject.parse(str);
		sec = j.getString("section");
		
		Section section = sd.querySection(sec);
		
		PrintWriter pw = response.getWriter();
//		int sectionId_ = Integer.parseInt(sectionId);
//		int sectionId = Integer.parseInt(request.getParameter("sectionId"));
		//根据版块id查询版主
//		int host = sd.querySectionHost(sectionId_);
		//根据版块id查询帖子
		if(sec.equals("全部")) {
			//查询所有帖子
			ArrayList<Card> ac = cd.queryCard(0);
			//查询所有版块名字
			ArrayList<Section> as = sd.querySection();
//			JSONArray ja1 = new JSONArray((List)as);
//			JSONArray ja2 = new JSONArray((List)ac);
			j = new JSONObject();
			j.put("section", as);
			j.put("card", ac);
//			System.out.print(((Card)j.getJSONArray("card").get(0)).getName());
			pw.println(j.toString());
			pw.flush();
			pw.close();
			//往session中传进ac,as
//			Map session = (Map) ActionContext.getContext().get("session");
//			session.put("card_list", ac);
//			session.put("section_list", as);		
			//往session中传进sectionId和host
//			session.put("sectionId", sectionId);
//			session.put("host", host);
		} else {
			//根据版块id查询帖子
			ArrayList<Card> ac = cd.querySectionCard(section.getId());
			j = new JSONObject();
			j.put("card", ac);
			pw.println(j.toJSONString());
			pw.flush();
			pw.close();
			//往session中传进ac,as
//			Map session = (Map) ActionContext.getContext().get("session");
//			session.put("card_list", ac);
			//往session中传进sectionId和host
//			session.put("sectionId", sectionId);
//			session.put("host", host);
		}
//		Map session = (Map) ActionContext.getContext().get("session");
//		UserTable u = (UserTable) session.get("user");
		//根据权限跳转主页
//		if(u != null && u.getPower() == 1)
//			response.sendRedirect("user_manage/main.jsp");
//			return "success_manage";
//		else
//			response.sendRedirect("user_common/main.jsp");
//			return "success_common";
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
}
