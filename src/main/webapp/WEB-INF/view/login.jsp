<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
 
</head>
<body>
	<form:form action="/login" method="post"> 
		<input type="text" name="id" id="id"/>
		<input type="password" name="pw" id="pw"/>
		<button type="submit">로그인</button>
	</form:form>

</body>
</html>
 