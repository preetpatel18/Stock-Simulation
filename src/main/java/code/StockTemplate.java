package code;

import java.io.IOException;

import yahoofinance.YahooFinance;

public class StockTemplate {
	
	private String symbol;
	private int numStocks;
	private double price = -1;
	private double totalPrice;
	
	public StockTemplate(String sym, int num) {
		symbol = new String(sym);
		numStocks = num;
		try {
			price = ((double)((int)(100 * YahooFinance.get(sym).getQuote().getPrice().doubleValue()))) / 100;
		} catch (IOException e) {
			e.printStackTrace();
		}
		totalPrice = ((double)((int)(100 * numStocks * price))) / 100;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getNumStocks() {
		return numStocks;
	}

	public double getPrice() {
		return price;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
	
}