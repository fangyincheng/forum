<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
     <title>修改密码</title>
  </head>
  <body>
  	<a href="person_change_password.jsp">修改密码</a>
  	<a href="../main?sectionId=-1">回到主页</a>
  	<s:fielderror fieldName="error_message"/>
  	<form action="../changePassword?path=user_common" method="post">
  		<table border="1">
  			<tr>
  				<td>原密码：</td>
  				<td><input type="password" name="old_password"/></td>
  			</tr>
  			<tr>
  				<td>新密码：</td>
  				<td><input type="password" name="new_password"/></td>
  			</tr>
  			<tr>
  				<td>确认密码：</td>
  				<td><input type="password" name="new_password_repeat"/></td>
  			</tr>
  			<tr>
  				<td><input type="submit" value="确认修改"/></td>
  			</tr>
  		</table>
  	</form>
  </body>
</html>