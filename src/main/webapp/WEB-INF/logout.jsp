<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Log Out</title>
</head>
<body>
	<%
	
	session.removeAttribute("username");
	session.removeAttribute("password");
	response.sendRedirect("about.html");
	session.invalidate();
	
	%>
</body>
</html>