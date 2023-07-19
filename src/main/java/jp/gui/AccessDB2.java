package jp.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccessDB2 {
	
	private static AccessDB2 instance;
	private Connection con;
	private String url;
	private String user;
	private String password;
	
	private AccessDB2() {
		url = "jdbc:postgresql://localhost:5432/postgres";
		user = "postgres";
		password = "postgrestest";
		
		connect();
		createTableIfNotExists();//テーブルが無かったら作る
		disconnect();
	}

	static synchronized AccessDB2 getInstance() {
		if(instance == null) {
			instance = new AccessDB2();
		}
		return instance;
	}

	public void connect() {
		try {
			con = DriverManager.getConnection(url,user,password);
			//System.out.println("データベースに接続しました");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			con.close();
			//System.out.println("データベース接続を閉じました");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTableIfNotExists() {
		String sql = "create table if not exists CD操作履歴"
				+ "( 商品No INTEGER, 商品名 VARCHAR(50), 処理 varchar(5), 在庫変更数 integer, 操作日時 timestamp(0));";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insertData(OperationHistory oh) {
		int count = 0;
		String sql = "insert into CD操作履歴"
				+ " values(?, ?, ?, ?, ?)";
		try(PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, oh.getItemNum());
			pstmt.setString(2, oh.getItemName());
			pstmt.setString(3, oh.getOperation());
			pstmt.setInt(4, oh.getStock());
			java.sql.Timestamp d = new java.sql.Timestamp(oh.getUpdate_date().getTime());
			pstmt.setTimestamp(5, d);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<OperationHistory> getData() {
		String sql = "select * from CD操作履歴";
		ArrayList<OperationHistory> list = new ArrayList<>();
		int itemNum;
		String itemName;
		String operation;
		int changeNum;
		Date date;
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet result = pstmt.executeQuery();){
			while(result.next()) {
				itemNum = result.getInt(1);
				itemName = result.getString(2);
				operation = result.getString(3);
				changeNum = result.getInt(4);
				date = result.getTimestamp(5);
				list.add(new OperationHistory(itemNum, itemName, operation, changeNum, date));
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return list;
	}
	
	public void getResentData(List<Object[]> list){
		String sql = "select * from CD操作履歴 order by 操作日時 desc";
		int itemNum;
		String itemName;
		String operation;
		int changeNum;
		Date date;
		SimpleDateFormat f = new SimpleDateFormat("yy/MM/dd HH:mm:ss"); 
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet result = pstmt.executeQuery();){
			while(result.next()) {
				itemNum = result.getInt(1);
				itemName = result.getString(2);
				operation = result.getString(3);
				changeNum = result.getInt(4);
				date = result.getTimestamp(5);
				Object[] object = {itemNum, itemName, operation, changeNum, f.format(date)};
				list.add(object);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	public int remakeTable(List<OperationHistory> list) {
		int count = 0;
		createTableIfNotExists();
		for(OperationHistory oh : list) {
			String sql = "INSERT INTO CD操作履歴 VALUES(?,?,?,?,?)";
			
			try (PreparedStatement pstmt = con.prepareStatement(sql); ) {

				pstmt.setInt(1,oh.getItemNum());//商品No
				pstmt.setString(2,oh.getItemName());//商品名
				pstmt.setString(3,oh.getOperation());
				pstmt.setInt(4,oh.getStock());
				java.sql.Timestamp t = new java.sql.Timestamp(oh.getUpdate_date().getTime());
				pstmt.setTimestamp(5,t);
				count += pstmt.executeUpdate();

			} catch ( SQLException e ) {
				//e.printStackTrace() ;
				return count;
			}
		}
		return count;
	}

}
