<%@ page import="java.util.ArrayList"%>
<%@ page import="code.StockTemplate"%>
<%@ page import="code.StockTransaction"%>
<%@ page import="code.StockTransactionTemplate"%>
<%@ page import="code.UserInformation"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="style.css" />
<meta charset="ISO-8859-1">
<title>View Stocks</title>
</head>
<body>
	<nav class="navi">
		<a class="index" href="home.jsp">Stock Trading Simulation</a>
		<ul>
			<li><p>
					<%
					if (session.getAttribute("username") == null) {
						response.sendRedirect("index.html");
						return;
					}
					NumberFormat formatter = NumberFormat.getCurrencyInstance();
					out.print("Balance: " + formatter.format(UserInformation.getBalance(session.getAttribute("username").toString())));
					%>
				</p></li>
			<li><a href="buystocks.jsp">Buy Stocks</a></li>
			<li><a href="sellstocks.jsp">Sell Stocks</a></li>
			<li><a href="settings.jsp">Settings</a></li>
		</ul>
	</nav>
	<div class="profile">
		<div class="view-stock owned">
			<%
			String user = session.getAttribute("username").toString();
			ArrayList<StockTemplate> stocks = StockTransaction.getStocks(user);
			out.print("<h3>Owned Stocks</h3>");
			out.print(
					"<table class = \"Title\"><tr><th>Symbol</th><th>Number</th><th>Price</th><th>Total Price</th></tr></table><div class='scroll'><table class='Stocks'>");
			for (int i = 0; i < stocks.size(); i++) {
				StockTemplate temp = stocks.get(i);
				out.print("<tr><td>" + temp.getSymbol().toUpperCase() + "</td> <td>" + temp.getNumStocks() + "</td> <td>"
				+ formatter.format(temp.getPrice()) + "</td> <td>" + formatter.format(temp.getTotalPrice())
				+ "</td> </tr>");
			}
			out.print("</table></div>");
			%>
		</div>

		<div class="view-stock history">
			<%
			ArrayList<StockTransactionTemplate> transactions = StockTransaction.getHistory(user);
			out.print("<h3>Transaction History</h3>");
			out.print("<table class = \"Title\"><tr><th class='small'>Symbol</th><th class='small'>Number</th>"
					+ "<th class='small'>Action</th><th class='medium'>Total Price</th><th class='large'>Date</th></tr>"
					+ "</table><div class= 'scroll'><table class ='Stocks'>");
			for (int i = 0; i < transactions.size(); i++) {
				StockTransactionTemplate temp = transactions.get(i);
				out.print("<tr><td class='small'>" + temp.getSymbol().toUpperCase() + "</td> <td class='small'>"
				+ temp.getNumStocks() + "</td> <td class='small'>" + temp.getAction() + "</td> <td class='medium'>"
				+ formatter.format(temp.getTotalPrice()) + "</td> <td class='large'>" + temp.getDate() + "</td></tr>");
			}
			out.print("</table></scroll>");
			%>
		</div>
	</div>
	<div class="background"><img alt="background" src="resources/beach.jpg"></div>-+-
</body>
</html>