package jp.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.TableRowSorter;

public class ShowPanel extends JPanel implements ActionListener {
	private GroupLayout groupLayout;//レイアウト
	AccessDB db;//データベース

	private List<Object[]> dataList;//全件リスト
	private String[] columnNames = {"商品番号", "商品名", "歌手名", "ジャンル", "金額", "在庫数", "取扱状況"};//列名

	private JButton allShowButton;//全表示

	private JLabel genreLabel;//ジャンルラベル
	private String[] genre = {"ジャンルを選択","J-POP","K-POP","洋楽","演歌・歌謡曲","童謡・唱歌","アニメ・ゲーム","ロック・ポップス","クラシック","ジャズ","ラップ・ヒップホップ","R&B・ソウル","ハードロック・メタル"};//ジャンル一覧
	private JComboBox<String> genreBox;//ジャンル選択

	private JLabel itemNumLabel;//商品番号ラベル
	private JTextField itemNumInput;//商品番号入力
	private JButton searchButton;//検索ボタン

	private JScrollPane showPane;//表示パネル
	private JTable table;//表示テーブル

	private CustomTableModel tableModel;//カスタムしたテーブル
	private TableRowSorter<CustomTableModel> sorter; 
	private int[] numericColumns = {0, 4, 5};

	/**
	 * Create the panel.
	 */
	public ShowPanel() {
		setBackground(new Color(204, 225, 153));
		db = AccessDB.getInstance();

		db.connect();
		dataList = db.showData();//データ取得
		db.disconnect();

		//全商品表示ボタン-----------------------------------------------
		allShowButton = new JButton("商品一覧");
		allShowButton.addActionListener(this);

		//-----ジャンル--------------------------------------------------
		genreLabel = new JLabel("ジャンル：");
		genreBox = new JComboBox<>(genre);
		genreBox.addActionListener(this);
		//---------------------------------------------------------------

		//-----商品番号検索----------------------------------------------
		itemNumInput = new JTextField();
		itemNumInput.setColumns(10);
		//エンターキーからの受付
		itemNumInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchByItemNumber();
				}
			}
		});
		itemNumLabel = new JLabel("商品番号：");
		searchButton = new JButton("検索");
		searchButton.addActionListener(this);
		//--------------------------------------------------------------

		//-----商品表示エリア-------------------------------------------
		showPane = new JScrollPane();
		//--------------------------------------------------------------

		//-----全体レイアウト-------------------------------------------
		groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(showPane, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(allShowButton)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(genreLabel)
										.addGap(2)
										.addComponent(genreBox, 0, 112, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(itemNumLabel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(itemNumInput, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(searchButton)))
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(searchButton)
								.addComponent(allShowButton)
								.addComponent(itemNumInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(genreBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(genreLabel)
								.addComponent(itemNumLabel))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(showPane, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addContainerGap())
				);

		//-----テーブル-------------------------------------------------------------
		//カスタムテーブルのインスタンス生成
		tableModel = new CustomTableModel(dataList, columnNames);
		table = new JTable(tableModel);
		// TableRowSorterを作成し、JTableに設定
		sorter = new TableRowSorter<>(tableModel);
		// 数値カラムのソートロジックを設定
		sortTable();
		showPane.setViewportView(table);
		add(showPane);
		//-----------------------------------------------------------------------------
		setLayout(groupLayout);

	}

	//-----アクション機能--------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent e) {

		// 全商品表示の場合
		if (e.getActionCommand().equals("商品一覧")) {
			tableModel = new CustomTableModel(dataList, columnNames);
			table.setModel(tableModel);
			sorter.setModel(tableModel); // sorterとの関連付けを再設定
			sortTable();

			// ジャンル選択の場合
		} else if (e.getSource() instanceof JComboBox) {
			String selectedGenre = (String) genreBox.getSelectedItem();

			// 【ジャンルを選択】を押した場合も全表示
			if(selectedGenre.equals("ジャンルを選択")) {
				tableModel = new CustomTableModel(dataList, columnNames);
				table.setModel(tableModel);
				sorter.setModel(tableModel); // sorterとの関連付けを再設定
				sortTable();
				
			}else {
				// 選択したジャンルのみ表示
				List<Object[]> filteredList = new ArrayList<>();
				for (Object[] row : dataList) {
					if (row[3].equals(selectedGenre)) {
						filteredList.add(row);
					}
				}
				tableModel = new CustomTableModel(filteredList, columnNames);
				table.setModel(tableModel);
				sorter.setModel(tableModel); // sorterとの関連付けを再設定
				sortTable();
				
			}

			// 商品番号検索の場合
		} else if (e.getActionCommand().equals("検索")) {//単体検索
			searchByItemNumber();
		}
	}

	// テーブルのソート
	private void sortTable() {
		for (int column : numericColumns) {
			sorter.setComparator(column, Comparator.naturalOrder());
		}
		//以下は↑の中身
//		sorter.setComparator(0, new Comparator<Integer>() {
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				return o1.compareTo(o2);
//			}
//		});
//		sorter.setComparator(4, new Comparator<Integer>() {
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				return o1.compareTo(o2);
//			}
//		});
//		sorter.setComparator(5, new Comparator<Integer>() {
//			@Override
//			public int compare(Integer o1, Integer o2) {
//				return o1.compareTo(o2);
//			}
//		});
		table.setRowSorter(sorter);
		sorter.toggleSortOrder(0);
	}

	// 商品番号検索用------------------------------------------------------------------------------------
	private void searchByItemNumber() {
		int searchItemNum = Integer.parseInt(itemNumInput.getText());
		List<Object[]> filteredList = new ArrayList<>();
		boolean found = false; // 商品番号が見つかったかどうかのフラグ
		for (Object[] row : dataList) {
			if (row[0].equals(searchItemNum)) {
				filteredList.add(row);
				found = true; // 商品番号が見つかった場合にフラグを立てる
				break;  // 一致する商品番号が見つかったらループを終了
			}
		}
		tableModel = new CustomTableModel(filteredList, columnNames);
		table.setModel(tableModel);
		sorter.setModel(tableModel); 
		if (!found) {
			// 商品番号が見つからなかった場合にダイアログを表示
			JOptionPane.showMessageDialog(this, "指定された商品番号は存在しません。", "検索結果", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//---------------------------------------------------------------------------------------------------------

}
