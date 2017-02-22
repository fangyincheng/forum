<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
     <title>主页</title>
  </head>
  <body>
  	选择版块：<a href="main?sectionId=-1">全部</a>
  	<c:forEach var="section" items="${sessionScope.section_list}">
  		<a href="main?sectionId=${section.id}">${section.name}</a>
	</c:forEach>
	<a href="main?sectionId=0">其他</a>
  	<br>
  	<a  href="../selfCard?sectionId=-1">我发的帖子</a>
  	<a  href="../extra?path=user/send_card.jsp">我要发帖</a>
  	<a  href="../extra?path=user_manage/person_change_password.jsp">个人中心</a>
  	<a  href="../outLogin">退出登陆</a>
  	<br>
  	<form action="../search" method="post">
  		<input type="text" name="key" size="18"/>
  		<input type="submit" value="搜索"/>
  		<br>
  	</form>
	<table border="1">
		<tr>
			<th>名字</th>
			<th>标题</th>
			<th>内容</th>
			<th>时间</th>
		</tr>
		<c:forEach var="card" items="${sessionScope.card_list}">
			<tr>
				<td>${card.name}</td>
				<td>${card.title}</td>
				<td>${card.contents}</td>
				<td>${card.date}</td>
				<td><a href="../lookReply?replyId=${card.id}">查看回复</a></td>
				<td><a href="../user/reply.jsp?replyId=${card.id}&sectionId=${card.section.id}">回复</a></td>
				<td><a href="../deleteCard?id=${card.id}&sectionId=${sessionScope.sectionId}&path=main">删除</a></td>
				<td><a href="../keepTop?id=${card.id}&sectionId=${sessionScope.sectionId}">置顶</a></td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>