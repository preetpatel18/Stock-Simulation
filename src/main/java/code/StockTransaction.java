package code;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import yahoofinance.YahooFinance;

public class StockTransaction {
	
	public static String buyStock(String symb, int num, String user, String pass) throws Exception{
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		
		Statement stmt = conn.createStatement();
		String totalUsers = "SELECT * from users";
		ResultSet userSheet = stmt.executeQuery(totalUsers);
		boolean userfound = false;
		double balance = 0;
		symb = symb.toUpperCase();
		
		while (userSheet.next())
		{
			String usernames = userSheet.getString(1);
			String passwords = userSheet.getString(2);
			balance = userSheet.getDouble(3);
			if(usernames.equals(user) && passwords.equals(pass)) {
				userfound = true;
				break;
			}
		}
		if(!userfound) {return "Username and Password incorrect";}
		
		String dirUserInfo = "SELECT * FROM " + user;
		ResultSet userinfo = stmt.executeQuery(dirUserInfo);
		
		double price;
		try {
			price =YahooFinance.get(symb).getQuote().getPrice().doubleValue();
		} catch(Exception e) {
			return "Stock not found";
		}
		double totalPrice = price * num;
		
		
		if(totalPrice > balance) {return "Not enough money";}
		boolean stockNotAdded = true;
		
		while (userinfo.next())
		{
			String stockSymbol = userinfo.getString(1);
			if(stockSymbol.equals(symb)) {
				stockNotAdded = false;
				PreparedStatement updateStock = conn.prepareStatement("update "  + user + " set Number=? where Stocks=? ");
				int numStocksOwned = userinfo.getInt(2);
				updateStock.setInt(1, numStocksOwned + num);
				updateStock.setString(2, symb);
				updateStock.executeUpdate();
			}
		}
		if(stockNotAdded) {
			stockNotAdded = false;
			PreparedStatement addStock = conn.prepareStatement("insert into " + user + " values(?,?)");
			addStock.setString(1, symb);
			addStock.setInt(2, num);
			addStock.executeUpdate();
		}
		
		if(!stockNotAdded) {
			PreparedStatement updateBalance = conn.prepareStatement("update users set Balance=? where username=? ");
			updateBalance.setDouble(1, balance - (totalPrice));
			updateBalance.setString(2, user);
			updateBalance.executeUpdate();
			PreparedStatement addHistory = conn.prepareStatement("insert into " + user + "history values(?,?,?,?,?)");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now(); 
			String time = dtf.format(now);
			addHistory.setString(1, symb);
			addHistory.setInt(2, num);
			addHistory.setString(3, "BUY");
			addHistory.setDouble(4, round(totalPrice));
			addHistory.setString(5, time);
			addHistory.executeUpdate();
			conn.close();
			return "yes";
		}
		conn.close();
		return "server error";
	}
	
	//-----------------------------------------------------------------------------------
	
	
	
	//-----------------------------------------------------------------------------------
	
	public static String sellStock(String symb, int num, String user, String pass) throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		
		Statement stmt = conn.createStatement();
		String totalUsers = "SELECT * from users";
		ResultSet userSheet = stmt.executeQuery(totalUsers);
		boolean userfound = false;
		double balance = 0;
		symb = symb.toUpperCase();
		
		while (userSheet.next()) //gets user information from users
		{
			String usernames = userSheet.getString(1);
			String passwords = userSheet.getString(2);
			balance = userSheet.getDouble(3);
			if(usernames.equals(user) && passwords.equals(pass)) {
				userfound = true;
				break;
			}
		}
		
		if(!userfound) {conn.close(); return "Username and Password incorrect";}
		
		String dirUserInfo = "SELECT * FROM " + user;
		ResultSet userinfo = stmt.executeQuery(dirUserInfo);
		
		double price;
		try {
			price = YahooFinance.get(symb).getQuote().getPrice().doubleValue();
		} catch(Exception e) {
			conn.close();
			return "Stock Not Found";
		}
		double totalPrice = round(num * price);
		
		boolean hasStock = false;
		int numStocks = -1;
		
		while(userinfo.next()) { //Getting the number of stocks owned
			String symbol = userinfo.getString(1);
			if(symbol.equals(symb)) {
				hasStock = true;
				numStocks = userinfo.getInt(2);
				break;
			}
		}
		
		if(numStocks < num) {conn.close(); return "Not enough stocks owned";}
		
		if(hasStock) {
			int postTransaction = numStocks - num;
			PreparedStatement updateStocks = conn.prepareStatement("update " + user + " set Number=? where Stocks=? ");
			updateStocks.setInt(1, postTransaction);
			updateStocks.setString(2, symb);
			updateStocks.executeUpdate();
			PreparedStatement deleteStock = conn.prepareStatement("DELETE FROM " + user + " WHERE Number = ?");
			deleteStock.setInt(1, 0);
			deleteStock.executeUpdate();
			PreparedStatement updateBalance = conn.prepareStatement("update users set Balance=? where username=? ");
			updateBalance.setDouble(1, round(balance + totalPrice));
			updateBalance.setString(2, user);
			updateBalance.executeUpdate();
			PreparedStatement addHistory = conn.prepareStatement("insert into " + user + "history values(?,?,?,?,?)");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			String time = dtf.format(now);
			addHistory.setString(1, symb);
			addHistory.setInt(2, num);
			addHistory.setString(3, "SELL");
			addHistory.setDouble(4, round(totalPrice));
			addHistory.setString(5, time);
			addHistory.executeUpdate();
			conn.close();
			return "yes";
		} else {
			conn.close();
			return "Stock Not Owned";
		}
	}
	
	public static ArrayList<StockTemplate> getStocks(String user) throws Exception {
		ArrayList<StockTemplate> arr = new ArrayList<StockTemplate>();
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		
		Statement stmt = conn.createStatement();
		ResultSet userStocks = stmt.executeQuery("SELECT * FROM " + user);
		
		while(userStocks.next()) {
			String symbol = userStocks.getString(1);
			int numStocks = userStocks.getInt(2);
			arr.add(new StockTemplate(symbol, numStocks));
		}
		conn.close();
		return arr;
	}
	
	public static ArrayList<StockTransactionTemplate> getHistory(String user) throws Exception {
		ArrayList<StockTransactionTemplate> arr = new ArrayList<StockTransactionTemplate>();
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		
		Statement stmt = conn.createStatement();
		ResultSet userStocks = stmt.executeQuery("SELECT * FROM " + user + "history");
		
		while(userStocks.next()) {
			String symbol = userStocks.getString(1);
			int numStocks = userStocks.getInt(2);
			String action = userStocks.getString(3);
			double totalPrice = userStocks.getDouble(4);
			String date = userStocks.getString(5);
			arr.add(new StockTransactionTemplate(symbol, numStocks, action, totalPrice, date));
		}
		conn.close();
		return arr;
	}
	
	private static double round(double num) {
		return ((double)((int)(num * 100 + .5))) / 100;
	}
	
	public static double getPrice(String symb) {
		double price = 0;
		try {
			price = YahooFinance.get(symb).getQuote().getPrice().doubleValue();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return StockTransaction.round(price);
	}

	
}