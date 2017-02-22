<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
     <title>论坛登录</title>
  </head>
  <body>
  	<s:fielderror fieldName="error_massage"/>
  	<s:fielderror fieldName="error_password"/>
  	<s:fielderror fieldName="error_username"/>
  	<s:form action="login" method="post">
  		<table>
  			<caption>用户登录</caption>
  			<tr>
  				<td>用户名：</td>
				<td><input type="text" name="username" size="20"/></td>
  			</tr>
  			<tr>
  				<td>密码：</td>
  				<td><input type="password" name="userpassword" size="20"/></td>
  			</tr>
  		</table>
  		<input type="submit" value="登录"/>
		<input type="reset" value="重置"/>
		&nbsp;没有账号？<a href="register.jsp">注册</a>
		或者<a href="main?sectionId=-1">游客登陆</a>
  	</s:form>
  </body>
</html>