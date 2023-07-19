package jp.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CustomTableModel  extends AbstractTableModel{

	private List<Object[]> dataList;	//テーブルには表示させないデータリスト
	private List<Object[]> showList;	//テーブルに表示させるデータリスト
	private String filterText = "";		//フィルター用のキーワード
	private String[] columnNames;		//列情報

	//リストと列名の配列で作る
	public CustomTableModel(List<Object[]> list,String[] columnNames){
		dataList = new ArrayList<Object[]>(list);
		showList = new ArrayList<Object[]>(list);
		this.columnNames = columnNames.clone();
		
//		// 商品番号,金額,在庫数の列（インデックス）のデータ型をIntegerに設定
//	    getColumnClass(0).equals(Integer.class);
//	    getColumnClass(4).equals(Integer.class);
//	    getColumnClass(5).equals(Integer.class);
	}
//
//	//二次元配列と列名で作る
//	public CustomTableModel(Object[][] obj,String[] columnNames){
//		dataList = new ArrayList<Object[]>();
//		showList = new ArrayList<Object[]>();
//		changeData(obj);
//		this.columnNames = columnNames.clone();
//	}
//
//	//列名だけで空のデータリストを作る
//	public CustomTableModel(String[] columnNames){
//		dataList = new ArrayList<Object[]>();
//		showList = new ArrayList<Object[]>();
//		this.columnNames = columnNames.clone();
//	}
//
//	//二次元配列で作るときはリストに配列データをセットする
//	private void changeData(Object[][] obj) {
//		int row = obj.length;
//		for(int i=0;i<row;i++) {
//			dataList.add(obj[i]);
//			showList.add(obj[i]);
//		}
//	}
//
//	//フィルターの文字列をフィールドに入れてから、フィルター処理のメソッドを呼ぶ
//	public void filterData(String filterText) {
//		this.filterText = filterText;
//		filterReset();
//	}
//
//	//全データを持っているdataListから、フィルターをかけて表示する内容だけのshowListを作る
//	private void filterReset() {
//		showList.clear();		//表示用リストの要素を一度全部消す
//		for (Object[] row : dataList) {	
//			int collength = row.length;
//			for(int i=0;i<collength;i++) {	//行ごとの各列を順番に見る
//				if (row[i].toString().contains(filterText)) {	//各列の中にフィルターキーワードが含まれている場合はshowListに入れる
//					showList.add(row);
//					break;
//				}
//			}
//
//		}
//
//		//テーブルの表示内容が変わることを通知する（これがないとテーブルに反映されない）
//		fireTableDataChanged(); 
//	}
//
//	//行データを追加する
//	public void addRow(Object[] rowData) {
//		//リストに追加
//		dataList.add(rowData);
//		//表示内容を更新する
//		filterReset();
//	}

	@Override
	public int getRowCount() {	//JTableが勝手に見に来る
		return showList.size();
	}

	@Override
	public int getColumnCount() {//JTableが勝手に見に来る
		return this.columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {//JTableが勝手に見に来る
		return showList.get(rowIndex)[columnIndex];
	}

	@Override
	public String getColumnName(int column) {//JTableが勝手に見に来る
		return this.columnNames[column];
	}

}