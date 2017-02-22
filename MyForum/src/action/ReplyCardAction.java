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
import beans.Section;
import beans.UserTable;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.CardDao;
import dao.SectionDao;
import dao.UserTableDao;

@SuppressWarnings("serial")
public class ReplyCardAction extends ActionSupport {
	
	private String replyId;
	private String sectionId;
	private String reply;
	
	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
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
		String name = j.getString("name");
		replyId = j.getString("replyId");
		sectionId = j.getString("sectionId");
		reply = j.getString("reply");
				
		PrintWriter pw = response.getWriter();
		
		int sectionId_ = Integer.parseInt(sectionId);
		//从session中获取当前用户对象
//		Map session = (Map) ActionContext.getContext().get("session");
//		UserTable u = (UserTable) session.get("user");
		
		UserTable u = new UserTableDao().queryUser(name);
		
		//建立帖子类对应数据库
		Card c = new Card();
//		c.setUserTable(new UserTableDao().queryUser(u.getUsername()));
		c.setNameId(u.getId());
		c.setName(u.getUsername());
		c.setContents(reply);
		c.setDate(new Date(System.currentTimeMillis()));
		c.setTitle("回复");
//		c.setSection(new SectionDao().querySection(sectionId_));
		c.setSectionId(sectionId_);
		c.setReplyId(Integer.parseInt(replyId));
		
//		if(reply.equals("")) {
//			addFieldError("send_error", "请填写内容！");
//			return Action.INPUT;
//		}
		
		if(new CardDao().insertCard(c)) {
			j = new JSONObject();
			j.put("result", "success");
//			response.sendRedirect("user/send_success.jsp");
//			return Action.SUCCESS;
		} else {
			j = new JSONObject();
			j.put("result", "error");
//			addFieldError("send_error", "发送错误！");
//			request.getRequestDispatcher("user/reply.jsp").forward(request, response);
//			return Action.INPUT;
		}
		pw.println(j.toString());
		pw.flush();
		pw.close();
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}

}
