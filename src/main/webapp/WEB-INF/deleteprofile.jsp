<%@ page import="code.UserInformation" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	if (session.getAttribute("username") == null) { response.sendRedirect("index.html"); return; }
	UserInformation.resetProfile(session.getAttribute("username").toString());
	session.invalidate();
	response.sendRedirect("index.html");
	%>
</body>
</html>