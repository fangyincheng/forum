package action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import beans.Card;
import beans.UserTable;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.CardDao;
import dao.SectionDao;
import dao.UserTableDao;

@SuppressWarnings("serial")
public class SendCardAction extends ActionSupport {
	
	private String title;
	private String content;
	private String select_section;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSelect_section() {
		return select_section;
	}

	public void setSelect_section(String select_section) {
		this.select_section = select_section;
	}

	public void write() throws Exception {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = ServletActionContext.getRequest();;
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/html;charset=UTF-8");
		InputStream inputStream = request.getInputStream();
//		byte[] b = new BufferedReader(new InputStreamReader(inputStream)).readLine().getBytes();
		byte[] b = IOUtils.toByteArray(inputStream);
		String str = new String(b,"UTF-8");
		str = str.substring(str.indexOf("{"),str.lastIndexOf("}")+1);
		JSONObject j = (JSONObject) JSONObject.parse(str);
		String sec = j.getString("section");
		String title = j.getString("title");
		String content = j.getString("content");
		String userId = j.getString("id");
		
		int userId_ = Integer.parseInt(userId);
//		int sectionId = Integer.parseInt(select_section);
		//从session中获取当前用户对象
//		Map session = (Map) ActionContext.getContext().get("session");
//		UserTable u = (UserTable) session.get("user");
		//建立帖子类对应数据库
		Card c = new Card();
		UserTableDao ut = new UserTableDao();
		SectionDao s = new SectionDao();
//		c.setUserTable(new UserTableDao().queryUser(u.getUsername()));
		UserTable u = ut.queryUser(ut.queryUserName(userId_));
		c.setNameId(u.getId());
		c.setName(u.getUsername());
		c.setContents(content);
		c.setDate(new Date(System.currentTimeMillis()));
		c.setTitle(title);
//		c.setSection(new SectionDao().querySection(sectionId));
		c.setSectionId(s.querySection(sec).getId());
		c.setReplyId(0);
		
//		if(title.equals("") || content.equals("")) {
//			addFieldError("error_message", "请填写完整!");
//			return Action.INPUT;
//		}
		PrintWriter pw = response.getWriter();
		if(new CardDao().insertCard(c)) {
			j = new JSONObject();
			j.put("result", "success");
			pw.write(j.toJSONString());
			pw.flush();
			pw.close();
//			response.sendRedirect("user/send_success.jsp");
//			return Action.SUCCESS;
		} else {
			j = new JSONObject();
			j.put("result", "error");
			pw.write(j.toJSONString());
			pw.flush();
			pw.close();
//			addFieldError("error_message", "帖子发送错误!");
//			request.getRequestDispatcher("user/send_card.jsp").forward(request, response);
//			return Action.INPUT;
		}
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
