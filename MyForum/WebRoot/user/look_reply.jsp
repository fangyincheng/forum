<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
     <title>查看回复</title>
  </head>
  <body>
  <table border="1">
		<caption>原帖子</caption>
		<tr>
			<th></th>
			<th>名字</th>
			<th>标题</th>
			<th>内容</th>
			<th>时间</th>
		</tr>
		<tr>
			<td><img src=""/></td>
			<td>${sessionScope.card.name}</td>
			<td>${sessionScope.card.title}</td>
			<td>${sessionScope.card.contents}</td>
			<td>${sessionScope.card.date}</td>
		</tr>
	</table>
	<table border="1">
		<caption>回复该帖子</caption>
		<tr>
			<th></th>
			<th>名字</th>
			<th>内容</th>
			<th>时间</th>
		</tr>
		<c:forEach var="card" items="${sessionScope.reply_list}">
			<tr>
				<td><img src=""/></td>
				<td>${card.name}</td>
				<td>${card.contents}</td>
				<td>${card.date}</td>
			</tr>
		</c:forEach>
	</table>
  </body>
</html>