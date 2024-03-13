<%@ page import="code.StockTransaction"%>
<%@ page import="code.UserInformation"%>
<%@ page import="java.text.NumberFormat"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Buy Stocks</title>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="style.css" />
</head>
<body>
	<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("about.html");
		return;
	}
	NumberFormat formatter = NumberFormat.getCurrencyInstance();
	%>
	<nav class="navi">
		<a class="index" href="home.jsp">Stock Trading Simulation</a>
		<ul>
			<li><p>
					<%
					out.print("Balance: " + formatter.format(UserInformation.getBalance(session.getAttribute("username").toString())));
					%>
				</p></li>
			<li><a href="buystocks.jsp">Buy Stocks</a></li>
			<li><a href="viewstocks.jsp">View Stocks</a></li>
			<li><a href="settings.jsp">Settings</a></li>
		</ul>
	</nav>
	<div class="signin">
		<div class="sellStocks">
			<h3 class="heading">Sell Stocks</h3>
			<form action="sellstock.jsp" method="post">
				<h2 class="sub-Heading">Abbreviated Name:</h2>
				<input type="text" class="usName" name="symbol" placeholder="example: goog, aapl" autocomplete="off" required />
				<h2 class="sub-Heading">Amount:</h2>
				<input type="number" name="numStocks" class="passWrd" placeholder="Number" autocomplete="off" required
					min="0" /> <input class="submit-btn" type="submit" name="submit"
					value="submit" />
			</form>
		</div>
	</div>
	<div class="background">
		<img alt="background" src="resources/beach.jpg">
	</div>
</body>
</html>
