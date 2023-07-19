package jp.gui;

//author:groupB
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class AccessDB {

	private static AccessDB instance;
	private Connection conn;
	private String url;
	private String user;
	private String password;
	//	private int numCounter;

	/*
	 * テーブル情報
	 * 商品No INTEGER, 商品名 VARCHAR(50), 歌手名 VARCHAR(50), ジャンル VARCHAR(10), 金額 INTEGER  ,在庫数 INTEGER
	 */

	private AccessDB() {
		url = "jdbc:postgresql://localhost:5432/postgres";
		user = "postgres";
		password = "postgrestest";

		//		CD.numCounter = 0;

		connect();
		createTableIfNotExists();
		disconnect();
	}

	static synchronized AccessDB getInstance() {
		if(instance == null) {
			instance = new AccessDB();
		}
		return instance;
	}

	public void connect() {
		try {
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("データベースに接続しました");
			//createTableIfNotExists();//テーブルが無かったら作る
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			conn.close();
			System.out.println("データベース接続を閉じました");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private void createTableIfNotExists() {

		String sql = "create table if not exists CD在庫管理 (商品No INTEGER primary key, 商品名 VARCHAR(50), 歌手名 VARCHAR(50), ジャンル VARCHAR(10), 金額 INTEGER  ,在庫数 INTEGER, 取扱状況 boolean)";

		//下記追加SQL文はテスト終了後に消去予定
		//		String sql = "create table if not exists CD在庫管理\n"
		//				+ "(  商品No INTEGER primary key, 商品名 VARCHAR(50), 歌手名 VARCHAR(50), ジャンル VARCHAR(10), 金額 INTEGER  ,在庫数 INTEGER);\n"
		//				+ "insert into CD在庫管理 values(1, 'aaa', 'aaa', '洋楽', 1000, 10);\n"
		//				+ "insert into CD在庫管理 values(2, 'bbb', 'bbb', 'アニメソング', 1500, 20);\n"
		//				+ "insert into CD在庫管理 values(3, 'ccc', 'ccc', '童謡', 800, 25);\n"
		//				+ "insert into CD在庫管理 values(4, 'ddd', 'ddd', 'J-POP', 2000, 30);\n"
		//				+ "insert into CD在庫管理 values(5, 'ddd', 'ddd', 'K-POP', 900, 30);\n"
		//				+ "insert into CD在庫管理 values(6, 'ddd', 'ddh', 'J-POP', 1000, 10);\n"
		//				+ "insert into CD在庫管理 values(7, 'aaa', 'apa', '洋楽', 1200, 15);\n"
		//				+ "insert into CD在庫管理 values(8, 'aaa', 'a', 'アニメソング', 1800, 100);\n"
		//				+ "insert into CD在庫管理 values(9, 'cc', 'scc', '童謡', 700, 25);";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//author:god_Sakamoto---------------------------------------------------------
	//検索用
	public CD selectData(int searchNum) {
		String sql = "select * from cd在庫管理 where 商品No = " + searchNum;
		CD cd = null;
		int itemNum = 0;
		String itemName = null;
		String artist = null;
		String genre = null;
		boolean handlingStatus = false;
		int price = 0;
		int stock = 0;
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet result = pstmt.executeQuery();){
			while(result.next()) {
				itemNum = result.getInt(1);
				itemName = result.getString(2);
				artist = result.getString(3);
				genre = result.getString(4);
				price = result.getInt(5);
				stock = result.getInt(6);
				handlingStatus = result.getBoolean(7);
			}
			cd = new CD(itemNum, itemName, artist, genre, price, stock, handlingStatus);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cd;
	}

	//追加用
	public int inventoryUpdate(int itemNum, int stock) {
		int count = 0;
		String sql = "update cd在庫管理 set 在庫数 = ? where 商品No = ?";

		try(PreparedStatement pstmt = conn.prepareStatement(sql);){
			pstmt.setInt(1, stock);
			pstmt.setInt(2,itemNum);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	//kitaguchi---sakamoto-------------------------------------------------------------------
	//商品登録
	int insertCDData(CD cd) {
		String sql = "INSERT INTO CD在庫管理 VALUES(?,?,?,?,?,?,?)";
		int count = 0;
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ) {

			pstmt.setInt(1,cd.getItemNum());//商品No
			pstmt.setString(2,cd.getItemName());//商品名
			pstmt.setString(3,cd.getArtist());//歌手名
			pstmt.setString(4,cd.getGenre());//ジャンル
			pstmt.setInt(5,cd.getPrice());//金額
			pstmt.setInt(6,0);//在庫数(数の上下は入荷出荷から)
			pstmt.setBoolean(7, true);//取扱状況初期値true
			count = pstmt.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		}	
		return count;//executeUpdateの結果を返す

	}

	//author:nishioka---------------------------------------------------------
	//表示用	
	public List<Object[]> showData() {
		String sql = "SELECT * FROM CD在庫管理 ORDER BY 商品No;";
		List<Object[]> cdList = new ArrayList<>();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet result = pstmt.executeQuery();

			while(result.next()) {
				int itemNum = result.getInt(1);
				String itemName = result.getString(2);
				String artist = result.getString(3);
				String genre = result.getString(4);
				int money = result.getInt(5);
				int stock = result.getInt(6);
				boolean handlingStatus = result.getBoolean(7);

				cdList.add(new Object[]{itemNum, itemName, artist, genre, money, stock, handlingStatus ? "取り扱い中" : "取り扱い停止中"});
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cdList;
	}

	public List<Object[]> showAlertData() {
		String sql = "SELECT 商品No,商品名,在庫数 FROM CD在庫管理 WHERE 在庫数 <= 3 AND 取扱状況 = true ORDER BY 在庫数 ASC,商品No ASC;";
		List<Object[]> cdList = new ArrayList<>();
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet result = pstmt.executeQuery();

			while(result.next()) {
				int itemNum = result.getInt(1);
				String itemName = result.getString(2);
				int stock = result.getInt(3);
				cdList.add(new Object[]{itemNum, itemName, stock});			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		return cdList;
	}

	//author:Okano,Yamamoto
	//編集用
	int updateCDData(String itemName, String artist, String genre, int money, int itemNumber, Boolean handlingStatusBool) {

		int count = 0;
		String sql = "update CD在庫管理 set 商品名 = ?, 歌手名 = ?, ジャンル = ?, 金額 = ?, 取扱状況 = ? where 商品no = ?";

		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, itemName);
			pstmt.setString(2, artist);
			pstmt.setString(3, genre);
			pstmt.setInt(4, money);
			pstmt.setBoolean(5, handlingStatusBool);
			pstmt.setInt(6, itemNumber);

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	//編集前のテキストフィールド書き出し用
	public Object[] inputtextfield(int itemNo) {

		String sql = "select * from CD在庫管理 where 商品No = "+ itemNo;

		try(PreparedStatement ps = conn.prepareStatement(sql);) {
			ResultSet result = ps.executeQuery();

			while(result.next()) {
				int itemNum=result.getInt(1);
				String itemname = result.getString(2);
				String artist = result.getString(3);
				//genre
				String genreinput = result.getString(4);
				int price = result.getInt(5);
				boolean handlingStatusBool = result.getBoolean(7);

				String handlingStatus;

				if(handlingStatusBool == true) {
					handlingStatus = "取り扱い中";
				} else {
					handlingStatus = "取り扱い停止中";
				}

				Object[] object = {itemNum,itemname,artist,genreinput,price,handlingStatus};
				return object;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}return null;

	}

	//o,y-------------------------------------------------------------------------

	//追加機能メソッド（阪本）
	public ArrayList<CD> getDBData(){//int sort, String option
		ArrayList<CD> list = new ArrayList<>();
		String sql = "select * from CD在庫管理 ORDER BY 商品no ASC";
		//		String where = "";
		int itemNum = 0;
		String itemName = null;
		String artist = null;
		String genre = null;
		boolean handlingStatus = false;
		int price = 0;
		int stock = 0;
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet result = pstmt.executeQuery();){
			while(result.next()) {
				itemNum = result.getInt(1);
				itemName = result.getString(2);
				artist = result.getString(3);
				genre = result.getString(4);
				price = result.getInt(5);
				stock = result.getInt(6);
				handlingStatus = result.getBoolean(7);
				CD cd = new CD(itemNum, itemName, artist, genre, price, stock, handlingStatus);
				list.add(cd);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return list;
	}

	public int remakeTable(List<CD> list) {//バックアップ復元用
		int count = 0;
		for(CD cd : list) {
			String sql = "INSERT INTO CD在庫管理 VALUES(?,?,?,?,?,?)";
			createTableIfNotExists();
			try (PreparedStatement pstmt = conn.prepareStatement(sql); ) {

				pstmt.setInt(1,cd.getItemNum());//商品No
				pstmt.setString(2,cd.getItemName());//商品名
				pstmt.setString(3,cd.getArtist());//歌手名
				pstmt.setString(4,cd.getGenre());//ジャンル
				pstmt.setInt(5,cd.getPrice());//金額
				pstmt.setInt(6,cd.getStock());//在庫数(数の上下は入荷出荷から)
				count += pstmt.executeUpdate();

			} catch ( SQLException e ) {
				//e.printStackTrace() ;
				return count;
			}
		}
		return count;
	}

	public int getNewItemNum() {//現在の商品Noの最大値を取って＋１して新しい商品番号をかえす
		int max = 0;
		String sql = "select  max(商品no) from CD在庫管理";
		try(PreparedStatement ps = conn.prepareStatement(sql);){
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				max = result.getInt(1)+1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return max;
	}
}

//-----使わなくなったメソッド---------------------------------------------------------------
//public int checkNum(CD cd) {
//		String sql = "select  max(商品no) from CD在庫管理";
//
//		try(PreparedStatement ps = conn.prepareStatement(sql);) {
//			ResultSet result = ps.executeQuery();
//
//			while(result.next()) {
//				int maxDBNum =result.getInt(1);
//				if(cd.getItemNum()<maxDBNum) {
//					int newitemNo = maxDBNum+2;
//					CD.itemNumCounter=newitemNo+1;
//					return newitemNo;
//					
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//
//		}
//		CD.itemNumCounter++;
//		return cd.getItemNum();
//	}
//}

//	public int countData() {//データ追加時の商品No取得用
//		String sql = "select count(*) from CD在庫管理";
//		int count = 0;
//		try(PreparedStatement pstmt = conn.prepareStatement(sql);
//				ResultSet result = pstmt.executeQuery();){
//			while(result.next()) {
//				count = result.getInt(1);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return count;
//	}

//public Object[] showDeleteData(int itemNo) {//ダイアログに削除商品表示用
//
//	String sql = "select * from CD在庫管理 where 商品No = "+ itemNo;
//
//	try(PreparedStatement ps = conn.prepareStatement(sql);) {
//		ResultSet result = ps.executeQuery();
//
//		while(result.next()) {
//			int itemNum=result.getInt(1);
//			String itemname = result.getString(2);
//			String artist = result.getString(3);
//			//genre
//			String genreinput = result.getString(4);
//			int price = result.getInt(5);
//
//			Object[] object = {itemNum,itemname,artist,genreinput,price};
//			return object;
//		}
//
//	} catch (SQLException e) {
//		e.printStackTrace();
//
//	}return null;
//}
//
//int deleteCDData(int itemNo) {//削除用
//	int count=0;
//	String sql ="delete from CD在庫管理 where 商品No = "+itemNo;
//	String sql2 ="select * from CD在庫管理 where 商品No = "+ itemNo;
//	try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//		count = pstmt.executeUpdate();
//
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//
//	return count;
//}