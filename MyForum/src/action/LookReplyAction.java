package action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import beans.Card;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.CardDao;

@SuppressWarnings("serial")
public class LookReplyAction extends ActionSupport {
	
	private String replyId;

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
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
		replyId = j.getString("replyId");
				
		PrintWriter pw = response.getWriter();
		
		int replyId_ = Integer.parseInt(replyId);
		
		CardDao cd = new CardDao();
//		Map session = (Map) ActionContext.getContext().get("session");
		//根据帖子id查询原帖子并存入session
		Card c = cd.queryCardById(replyId_);
//		session.put("card", c);
		//根据帖子id作为回复id查询回复帖子并存入session
		ArrayList<Card> arr = cd.queryCard(replyId_);
		j = new JSONObject();
		j.put("reply_arr", arr);
		j.put("c", c);
		System.out.print(j.toJSONString());
		pw.println(j.toString());
		pw.flush();
		pw.close();
//		session.put("reply_list", arr);
		
//		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
	
}
