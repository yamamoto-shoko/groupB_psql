package jp.gui;

import java.util.Date;

public class OperationHistory {
	
	private int itemNum;
	private String itemName;
	private String operation;
	private int stock;
	private Date update_date;
	
	OperationHistory(int itemNum ,String itemName, String operation, int stock, Date update_date){
		setItemNum(itemNum);
		setItemName(itemName);
		setOperation(operation);
		setStock(stock);
		setUpdate_date(update_date);
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	

}
