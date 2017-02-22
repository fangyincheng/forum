<%@ page language="java" pageEncoding="gb2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
     <title>论坛发帖</title>
  </head>
  <body>
  	<s:fielderror fieldName="error_message"/>
  	<form action="../sendCard" method="post">
  		<table>
  			<caption>用户发帖</caption>
  			<tr>
  				<td>标题：</td>
				<td><input type="text" name="title" size="20"/></td>
  			</tr>
  			<tr>
  				<td>内容：</td>
  				<td><input type="text" name="content" size="20"/></td>
  			</tr>
  			<tr>
  				<td>所属版块：</td>
  				<td>
  					<select name="select_section">
  						<c:forEach var="section" items="${sessionScope.section_list}">
							<option value="${section.id}">${section.name}</option>
						</c:forEach>
  						<option value="0">其他</option>
  					</select>
  				</td>
  			</tr>
  		</table>
  		<input type="submit" value="发表"/>
		<input type="reset" value="重置"/>
  	</form>
  </body>
</html>