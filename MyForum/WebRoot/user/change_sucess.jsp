<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="beans.UserTable"%>
<html>
	<head>
     <title>修改成功</title>
  </head>
  <body>
  	修改成功,点击
  	<c:if test="${sessionScope.user.power==0}">
  		<a href="../user_common/person_change_password.jsp">这里</a>
  	</c:if>
  	<c:if test="${sessionScope.user.power==1}">
  		<a href="../user_manage/person_change_password.jsp">这里</a>
  	</c:if>
  	返回个人中心！
  </body>
</html>