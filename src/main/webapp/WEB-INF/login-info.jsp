<%@page import="java.text.NumberFormat" %>
<%@page import="code.UserInformation" %>
<%@page import="code.StockTransaction" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<link rel="stylesheet" href="style.css" />
</head>
<body>
	<%
	if (session.getAttribute("username") == null) { response.sendRedirect("about.html"); return; }
	NumberFormat formatter = NumberFormat.getCurrencyInstance();
	
	%>
	<nav class="navi">
		<p class="index">Stock Trading Simulation</p>
		<ul>
			<li><p><%out.print("Balance: " + formatter.format(UserInformation.getBalance(session.getAttribute("username").toString())));%></p></li>
			<li><a href="settings.jsp">Settings</a></li>
			<li><a href="about-after-Sign-In.html">About</a></li>
			<li><a href="logout.jsp">Log Out</a></li>
		</ul>
	</nav>
	<div class="options">
		<ul>
			<li><a href="buystocks.jsp"> Buy Stocks </a>
			<li><a href="sellstocks.jsp"> Sell Stocks</a>
			<li><a href="viewstocks.jsp"> View Stocks</a>
		</ul>
	</div>
	<div class="background"><img src="resources/beach.jpg"></div>
</body>
</html>