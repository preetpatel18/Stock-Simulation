<%@ page import="code.UserInformation"%>
<%@ page import="code.StockTransaction"%>
<%@ page import="code.StockTransactionTemplate"%>
<%@ page import="code.StockTemplate"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.NumberFormat" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css" />
<meta charset="ISO-8859-1">
<title>Settings</title>
</head>
<body>
	<% if (session.getAttribute("username") == null) { response.sendRedirect("index.html"); return; } NumberFormat formatter = NumberFormat.getCurrencyInstance();%>
	
	<nav class="navi">
		<a class="index" href="home.jsp">Stock Trading Simulation</a>
		<ul>
			<li><p><%out.print("Balance: " + formatter.format(UserInformation.getBalance(session.getAttribute("username").toString())));%></p></li>
		</ul>
	</nav>
	<div class="settings">
		<ul>
		<li><p><a href="deletehistory.jsp">DELETE HISTORY</a></p></li>
		<li><p><a href="deleteprofile.jsp">DELETE PROFILE</a></p></li>
		</ul>
	</div>
	<div class="background"><img alt="background" src="resources/beach.jpg"></div>
</body>
</html>