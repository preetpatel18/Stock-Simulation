<%@ page import="code.StockTransaction"%>
<%@ page import="code.UserInformation" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css" />
<meta charset="ISO-8859-1">
<title>Sell Stocks</title>
</head>
<body>
<% if (session.getAttribute("username") == null) { response.sendRedirect("index.html"); return; } NumberFormat formatter = NumberFormat.getCurrencyInstance();%>
	<nav class="navi">
		<a class="index" href="home.jsp">Stock Trading Simulation</a>
		<ul>
			<li><p><%out.print("Balance: " + formatter.format(UserInformation.getBalance(session.getAttribute("username").toString())));%></p></li>
			<li><a href="buystocks.jsp">Buy Stocks</a></li>
			<li><a href="viewstocks.jsp">View Stocks</a></li>
			<li><a href="settings.jsp">Settings</a></li>
		</ul>
	</nav>
	<div class="signin">
		<%
		String symbol = request.getParameter("symbol");
		int numStocks = Integer.parseInt(request.getParameter("numStocks"));
		String user = session.getAttribute("username").toString();
		String pass = session.getAttribute("password").toString();
		String error = StockTransaction.sellStock(symbol, numStocks, user, pass);
		if(error.equals("yes")) {
			out.print("<table class='transaction'><tr><th><p>Transaction Successful</p></th></tr>");
			double price = StockTransaction.getPrice(symbol);
			out.print("<tr><td><p>You Sold " + numStocks + " " + symbol.toUpperCase() + " stocks for a total of " + formatter.format(price * numStocks) + "</p></td></tr>");
			out.print("<tr><td><p>Balance: " + formatter.format(UserInformation.getBalance(user)) + "</p></tr></td>");
			out.print("<tr><td><a href='home.jsp'><button>Close</button></a></tr></td></table>");
		} else {
			out.print("<h3>Transaction Unsuccessful</h3>");
			out.print("<p>" + error + "</p>");
			out.print("<a class = \"sign-in\" href = \"sellstocks.jsp\"><button>Close</button></a>");
		}
		%>
	</div>
	<div class="background">
		<img alt="background" src="resources/stocksbackground.png">
	</div>
</body>
</html>