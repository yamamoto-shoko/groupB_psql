package jp.gui;

public class CD {

	private int itemNum ;
	private String itemName;
	private String artist;
	private String genre;
	private int price;
	private int stock;
	private boolean handlingStatus;



	CD(int itemNum, String itemName, String artist, String genre, int price){//追加登録時使用想定（InsertPanel）
		setItemNum(itemNum);
		setItemName(itemName);
		setArtist(artist);
		setGenre(genre);
		setPrice(price);
	}

	CD(int itemNum, String itemName, String artist, String genre, int price, int stock, boolean handlingStatus){//データ取得用
		setItemNum(itemNum);
		setItemName(itemName);
		setArtist(artist);
		setGenre(genre);
		setPrice(price);
		setStock(stock);
		setHandlingStatus(handlingStatus);
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

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public boolean getHandlingStatus() {
		return handlingStatus;
	}

	public void setHandlingStatus(boolean handlingStatus) {
		this.handlingStatus = handlingStatus;
	}

}