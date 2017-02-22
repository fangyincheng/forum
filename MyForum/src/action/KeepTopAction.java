package action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import dao.CardDao;

@SuppressWarnings("serial")
public class KeepTopAction extends ActionSupport {
	
	private String id;
	private String sectionId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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
		id = j.getString("id");
		
		//获取发贴人id
		int id_ = Integer.parseInt(id);
		PrintWriter pw = response.getWriter();
		CardDao cd = new CardDao();
		if(cd.updateTop(id_, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))))
		{
			j = new JSONObject();
			j.put("result", "success");
		} else {
			j = new JSONObject();
			j.put("result", "error");
		}
		pw.write(j.toJSONString());
		pw.flush();
		pw.close();
//		response.sendRedirect("MainServlet?sectionId=" + request.getParameter("sectionId"));
//		return Action.SUCCESS;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}

}
