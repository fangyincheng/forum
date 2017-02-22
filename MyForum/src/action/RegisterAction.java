package action;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import beans.UserTable;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import dao.UserTableDao;

@SuppressWarnings("serial")
public class RegisterAction extends ActionSupport {
	
	private String username;
	private String userpassword;
	private String userpassword2;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUserpassword2() {
		return userpassword2;
	}

	public void setUserpassword2(String userpassword2) {
		this.userpassword2 = userpassword2;
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
		username = j.getString("username");
		userpassword = j.getString("userpassword");
		//建立用户类对应数据库
		UserTable u = new UserTable();
		u.setUsername(username);
		u.setPassword(userpassword);
		u.setPower(0);
		
		UserTableDao ud = new UserTableDao();
		
		PrintWriter pw = response.getWriter();
		if(ud.queryUser(username)!=null) {
			j = new JSONObject();
			j.put("result", "user_error");
		} else if(ud.insertUser(u)) {
			j = new JSONObject();
			j.put("result", "success");
		} else {
			j = new JSONObject();
			j.put("result", "error");
		}
		pw.write(j.toJSONString());
		pw.flush();
		pw.close();
		
//		if(username.equals("") || userpassword.equals("") || userpassword2.equals(""))
//			addFieldError("error_message", "请填写完整！");
//		else if(!userpassword.equals(userpassword2))
//			addFieldError("error_message", "请输入相同密码！");
//		else 
//			if(ud.queryUser(username)==null && ud.insertUser(u)) {
//			return Action.SUCCESS;
//		} else{
//			addFieldError("error_message", "注册失败，因为用户已存在！");
//		}
//		return Action.INPUT;
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
}
