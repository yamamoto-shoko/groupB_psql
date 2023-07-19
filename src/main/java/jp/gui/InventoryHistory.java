package jp.gui;

import java.util.Date;

public class InventoryHistory {
	
	private int itemNum;
	private String itemName;
	private String inventory;
	private int stock;
	private Date update_date;
	
	InventoryHistory(int itemNum ,String itemName, String inventory, int stock, Date update_date){
		setItemNum(itemNum);
		setItemName(itemName);
		setInventory(inventory);
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

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
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
