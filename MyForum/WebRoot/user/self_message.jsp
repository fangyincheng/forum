<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
     <title>我的帖子</title>
  </head>
  <body>
  	选择版块：<a href="selfCard?sectionId=-1">全部</a>
  	<c:forEach var="section" items="${sessionScope.section_list}">
  		<a href="selfCard?sectionId=${section.id}">${section.name}</a>
	</c:forEach>
	<a href="selfCard?sectionId=0">其他</a>
	<br>
  	<a  href="user/send_card.jsp">我要发帖</a>
  	<a href="main?sectionId=-1">回到主页</a>
	<table border="1">
		<tr>
			<th>名字</th>
			<th>标题</th>
			<th>内容</th>
			<th>时间</th>
		</tr>
		<c:forEach var="card" items="${sessionScope.self_card_list}">
			<tr>
				<td>${card.name}</td>
				<td>${card.title}</td>
				<td>${card.contents}</td>
				<td>${card.date}</td>
				<td><a href="lookReply?replyId=${card.id}">查看回复</a></td>
				<td><a href="deleteCard?id=${card.id}&sectionId=${sessionScope.sectionId}&path=selfCard">删除</a></td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>