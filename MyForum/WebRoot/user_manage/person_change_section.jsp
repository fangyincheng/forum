<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
     <title>管理版块</title>
  </head>
  <body>
  	<a href="person_change_password.jsp">修改密码</a>
  	<a href="../changeUser">管理用户</a>
  	<a href="../changeSection">管理版块</a>
  	<a href="../main?sectionId=-1">回到主页</a>
  	<br>
  	<table border="1">
  		<tr>
  			<th>版块</th>
  			<th>版主</th>
  		</tr>
  		<c:forEach var="section" items="${sessionScope.section_list}">
			<tr>
				<td>${section.name}</td>
				<td>${section.hostName}</td>
				<td><a href="../deleteSection?id=${section.id}">删除</a></td>
			</tr>
		</c:forEach>
  	</table>
  	<br>
  	${sessionScope.No_user}
  	<%session.setAttribute("No_user", ""); %>
  	<form action="../updateSection" method="post">
  		版块名：<input type="text" name="name" size="18"/>
  		版主名：<input type="text" name="hostName" size="18"/>
  		<br>
  		<br>
  		<input type="submit" value="增加版块/修改版主"/>
    </form>
  </body>
</html>