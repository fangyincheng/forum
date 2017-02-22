<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
     <title>回复帖子</title>
  </head>
  <body>
  <s:fielderror fieldName="send_error"/>
  	<form action="../replyCard?replyId=<%=request.getParameter("replyId") %>&sectionId=<%=request.getParameter("sectionId") %>" method="post">
		请输入回复内容：
		<input type="text" name="reply"/>
		<input type="submit" value="回复"/>
	</form>
  </body>
</html>