package action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import beans.UserTable;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.UserTableDao;

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport {
	
	private String username;
	private String userpassword;
	
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
		username = j.getString("name");
		userpassword = j.getString("password");
		
		UserTableDao ud = new UserTableDao();
		
		PrintWriter pw = response.getWriter();
		
//		if(username.equals("") || userpassword.equals(""))
//			addFieldError("error_massage", "请填写完整!");
//		else 
			if(ud.queryUser(username) != null) {
			//从数据库获取用户信息
			UserTable u = ud.queryUser(username, userpassword);
			if(u == null) {
				j = new JSONObject();
				j.put("result", "password_error");
				pw.flush();
				pw.close();
//				addFieldError("error_password", this.getText("login.passworderror"));
			} else {
//				Map session = (Map) ActionContext.getContext().get("session");
//				session.put("user", u);
//				return Action.SUCCESS;
				j = new JSONObject();
				j.put("result", "success");
				j.put("user", u);
				pw.println(j.toJSONString());
				System.out.print(j.toJSONString());
				pw.flush();
				pw.close();
			}
				
		} else {
			j = new JSONObject();
			j.put("result", "user_error");
			pw.println(j.toJSONString());
			pw.flush();
			pw.close();
//			addFieldError("error_username", this.getText("user.no"));
		}
//		return Action.INPUT;
	}
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		super.validate();
	}
}
