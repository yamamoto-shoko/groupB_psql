package jp.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class InformationPanel extends JPanel implements ActionListener{
	//西岡-------------------------------------------------------------------------------
	AccessDB db;//データベース
	private List<Object[]> stockList;//在庫おしらせリスト(３件以下)
	private JTable stockTable;//在庫おしらせテーブル
	private String[] stockColumnNames = {"商品番号", "商品名", "在庫数"};//列名
	private CustomTableModel stockTableModel;//カスタムしたテーブル
	private TableRowSorter<CustomTableModel> stockSorter; //ソート
	
	//坂本------------------------------------------------------------------------------
	AccessDB2 db2;
	private List<Object[]> recentList;
	private JTable recentTable;
	private String[] recentColumnNames = {"商品番号", "商品名", "処理", "在庫変更数", "操作日時"};
	//DefaultTableModel model = new DefaultTableModel(null, columnNames);
	private CustomTableModel recentTableModel;
	private TableRowSorter<CustomTableModel> recentSorter; 

	/**
	 * Create the panel.
	 */
	public InformationPanel() {
		//西岡-------------------------------------------
		db = AccessDB.getInstance();
		db.connect();
		stockList = db.showAlertData();//データ取得
		db.disconnect();
		
		//坂本-------------------------------------------
		recentList = new ArrayList<>();
		db2 = AccessDB2.getInstance();
		db2.connect();
		db2.getResentData(recentList);
		db2.disconnect();

		setBorder(new LineBorder(new Color(87, 84, 74), 2, true));
		setBackground(new Color(218, 212, 187));

		JScrollPane stockPane = new JScrollPane();

		JScrollPane recentPane = new JScrollPane();

		JLabel recentLabel = new JLabel("直近で操作があったモノ");

		JLabel stockLabel = new JLabel("在庫数の少ないモノ");
		
		GroupLayout allLayout = new GroupLayout(this);
		allLayout.setHorizontalGroup(
				allLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(allLayout.createSequentialGroup()
						.addGroup(allLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(allLayout.createSequentialGroup()
										.addGap(60)
										.addComponent(recentLabel, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
										.addGap(111))
								.addGroup(allLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(recentPane, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.RELATED)))
						.addGroup(allLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(allLayout.createSequentialGroup()
										.addGap(14)
										.addComponent(stockLabel, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
								.addGroup(allLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(stockPane, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))
						.addContainerGap())
				);
		allLayout.setVerticalGroup(
				allLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(allLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(allLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(recentLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(stockLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(allLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(recentPane, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
								.addComponent(stockPane, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
						.addContainerGap())
				);
		
		stockTable = new JTable();
		stockPane.setViewportView(stockTable);
		
		recentTable = new JTable();
		recentPane.setColumnHeaderView(recentTable);
		
		setLayout(allLayout);
		
		//西岡-----------------------------------------------------------------------------
		//カスタムテーブルのインスタンス生成
		stockTableModel = new CustomTableModel(stockList, stockColumnNames);
		stockTable = new JTable(stockTableModel);
		
		// TableColumnModelを取得
		TableColumnModel columnModel = stockTable.getColumnModel();

		// 列ごとに幅を設定
		columnModel.getColumn(0).setPreferredWidth(30);  // 列0の幅を100に設定
		columnModel.getColumn(1).setPreferredWidth(100);  // 列1の幅を200に設定
		columnModel.getColumn(2).setPreferredWidth(15);

		// TableRowSorterを作成し、JTableに設定
		stockSorter = new TableRowSorter<>(stockTableModel);
		
		// 数値カラムのソートロジックを設定
		int[] numericColumns = {0, 2};
		for (int column : numericColumns) {
			stockSorter.setComparator(column, Comparator.naturalOrder());
		}
		
		stockTable.setRowSorter(stockSorter);
		stockSorter.toggleSortOrder(0);
		stockPane.setViewportView(stockTable);
		add(stockPane);
		//--------------------------------------------------------------------------------

		//阪本----------------------------------------------------------------------------
		recentTableModel = new CustomTableModel(recentList, recentColumnNames);
		recentTable = new JTable(recentTableModel);
		
		TableColumnModel columnModel2 = recentTable.getColumnModel();

		columnModel2.getColumn(0).setPreferredWidth(35);  
		columnModel2.getColumn(1).setPreferredWidth(80); 
		columnModel2.getColumn(2).setPreferredWidth(15);
		columnModel2.getColumn(3).setPreferredWidth(45);
		columnModel2.getColumn(4).setPreferredWidth(80);

		recentSorter = new TableRowSorter<>(recentTableModel);
		
		int[] numericColumns2 = {0,1,3,4};
		for (int column : numericColumns2) {
			recentSorter.setComparator(column, Comparator.naturalOrder());
		}
		
		recentTable.setRowSorter(recentSorter);
		recentSorter.toggleSortOrder(0);
		recentPane.setViewportView(recentTable);
		add(recentPane);
		
		//---------------------------------------------------------------------------------
	}
//-------------------------------------------------------------------------------------------
	
	//未使用
	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
