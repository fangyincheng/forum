<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
     <title>论坛注册</title>
  </head>
  <body>
	<s:fielderror />
  	<form action="register" method="post">
  		<table>
  			<caption>用户注册</caption>
  			<tr>
  				<td>用户名：</td>
				<td><input type="text" name="username" size="20"/></td>
  			</tr>
  			<tr>
  				<td>密码：</td>
  				<td><input type="password" name="userpassword" size="20"/></td>
  			</tr>
  			<tr>
  				<td>确认密码：</td>
  				<td><input type="password" name="userpassword2" size="20"/></td>
  			</tr>
  		</table>
  		<input type="submit" value="注册"/>
		<input type="reset" value="重置"/>
		<a  href="login.jsp">我要登陆</a>
  	</form>
  </body>
</html>