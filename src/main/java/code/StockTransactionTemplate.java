package code;

public class StockTransactionTemplate {
	
	private String symbol;
	private int numStocks;
	private String action;
	private double totalPrice;
	private String date;
	
	public StockTransactionTemplate(String sym, int num, String act, double totalPrice, String date) {
		symbol = new String(sym);
		numStocks = num;
		action = act;
		this.date = date;
		this.totalPrice = totalPrice;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getNumStocks() {
		return numStocks;
	}

	public String getDate() {
		return date;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public String getAction() {
		return action;
	}

}