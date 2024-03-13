<%@ page import="code.StockTransaction"%>
<%@ page import="code.UserInformation" %>
<%@ page import="java.text.NumberFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Buy Stocks</title>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="style.css" />
</head>
<% if (session.getAttribute("username") == null) { response.sendRedirect("about.html"); return; } NumberFormat formatter = NumberFormat.getCurrencyInstance();
%>
<body>
	<nav class="navi">
		<a class="index" href="home.jsp">Stock Trading Simulation</a>
		<ul>
			<li><p><%out.print("Balance: " + formatter.format(UserInformation.getBalance(session.getAttribute("username").toString())));%></p></li>
			<li><a href="sellstocks.jsp">Sell Stocks</a></li>
			<li><a href="viewstocks.jsp">View Stocks</a></li>
			<li><a href="settings.jsp">Settings</a></li>
		</ul>
	</nav>
	<div class="signin">
		<div class="buystock">
			<h3 class="heading">Buy Stocks</h3>
			<form action="buystock.jsp" method="post">
				<h2 class="sub-Heading">Abbreviated Name:</h2>
					<input class="usName" placeholder="example: goog, aapl" type="text" name="symbol" autocomplete="off"
							required />
				<h2 class="sub-Heading">Amount:</h2>
					<input type="number" class="passWrd" name="numStocks" placeholder="Number" autocomplete="off" required min="0" />
						<input class="submit-btn" type="submit" name="submit" value="Submit" />
			</form>
		</div>
	</div>
	<div class="background"><img src="resources/beach.jpg"></div>
	
</body>
</html>