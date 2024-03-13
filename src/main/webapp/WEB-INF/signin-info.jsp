<%@ page import="java.sql.*"%>
<%@ page import="code.UserInformation"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="stylesheet" href="style.css" />
<title>sign in</title>
</head>
<body>
	<nav class="navi">
		<p class="index">Stock Trading Simulation</p>
		<ul>
			<li><a href="about.html">About</a></li>
			<li><a href="index.html">Home</a></li>
			<li><a href="login.html">Log in</a></li>
		</ul>
	</nav>
	<div class="background"><img alt="background" src="resources/stocksbackground.png"></div>
	<div class="signin">
		<%
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		String error = UserInformation.addUser(user, pass);
		if (error.equals("yes")) {
			session.setAttribute("username", user);
			session.setAttribute("password", pass);
			response.sendRedirect("home.jsp");
		} else {
			out.println("<p>" + error + "</p>");
		}
		%>
		<a class="return" href="signin.html"> Return to Sign Up</a>
	</div>
</body>
</html>