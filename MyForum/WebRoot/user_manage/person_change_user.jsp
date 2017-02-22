<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
     <title>管理用户</title>
  </head>
  <body>
  	<a href="person_change_password.jsp">修改密码</a>
  	<a href="../changeUser">管理用户</a>
  	<a href="../changeSection">管理版块</a>
  	<a href="../main?sectionId=-1">回到主页</a>
  	<br>
  	<table border="1">
  		<caption>所有用户</caption>
  		<tr>
  			<th>用户名</th>
  			<th>权限</th>
  		</tr>
  		<c:forEach items="${sessionScope.user_list}" var="user">
  			<tr>
  				<td>${user.username}</td>
  				<td>
	  				<c:if test="${user.power == 0}">
	  					普通
	  				</c:if>
	  				<c:if test="${user.power == 1}">
	  					管理员
	  				</c:if>
	  				<c:if test="${user.power == 2}">
	  					版主
	  				</c:if>
	  			</td>
  				<td><a href="../deleteUser?id=${user.id}">删除</a></td>
  				<c:if test="${user.power == 1}">
  					<td><a href="../updateUser?power=0&id=${user.id}">除去管理员权限</a></td>
  				</c:if>
  				<c:if test="${user.power != 1}">
  					<td><a href="../updateUser?power=1&id=${user.id}">设为管理员</a></td>
  				</c:if>
  			</tr>
  		</c:forEach>
  	</table>
  	<br>
  	${sessionScope.error_m}
  	<%session.setAttribute("error_m", ""); %>
  	<form action="../addUser" method="post">
  		用户名：<input type="text" name="name" size="18"/>
  		密码：<input type="password" name="password" size="18"/>
  		<br>
  		<br>
  		<input type="submit" value="添加用户"/>
  	</form>
  </body>
</html>