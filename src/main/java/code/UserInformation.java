package code;

import java.sql.*;

public class UserInformation {
	
	
	public static String addUser(String user, String pass) throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT * from users";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next())
		{
			String usernames = rs.getString(1);
			String passwords = rs.getString(2);
			if(usernames.equalsIgnoreCase(user)) {
				if(passwords.equals(pass)) {
					conn.close();
					return "yes";
				}
				conn.close();
				return "Username taken";
			}
		}
		
		if(pass.length() < 8) {
			return "Password too short";
		}
		
		String createTable = "CREATE TABLE " + user + " ( Stocks text, Number Int);";
		stmt.execute(createTable);
		String createHistory = "CREATE TABLE " + user + "history (stocks text, number Int, action text, totalPrice double, date text);";
		stmt.execute(createHistory);
		
		PreparedStatement userupdate = conn.prepareStatement("insert into users values(?,?,?)");
		
		userupdate.setString(1,user);
		userupdate.setString(2,pass);
		userupdate.setDouble(3, 10000);
		userupdate.executeUpdate();
		
		conn.close();
		return "yes";
	}
	
	public static String login(String user, String pass) throws Exception{
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT * from users";
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			String usernames = rs.getString(1);
			String passwords = rs.getString(2);
			if(usernames.equals(user) && passwords.equals(pass)) {
				conn.close();
				return "yes";
			}
		}
		
		conn.close();
		return "incorrect password";
	}
	
	public static double getBalance(String user) throws Exception{
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM users");
		double balance = -1;
		while(rs.next()) {
			if(rs.getString(1).equals(user)) {
				balance = rs.getDouble(3);
				break;
			}
		}
		
		conn.close();
		return balance;
		
	}
	
	public static void deleteHistory(String user) throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("TRUNCATE TABLE " + user + "history");
		conn.close();
	}
	
	public static void resetProfile(String user) throws Exception {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userinfo";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";
		Class.forName(driver).getDeclaredConstructor().newInstance();
		conn = DriverManager.getConnection(url+dbName,userName,password);
		Statement stmt = conn.createStatement();
		
		
		stmt.executeUpdate("DROP TABLE " + user + "history");
		stmt.executeUpdate("DROP TABLE " + user);
		PreparedStatement delete = conn.prepareStatement("DELETE FROM users WHERE username = ?");
		delete.setString(1, user);
		delete.executeUpdate();
	}

}