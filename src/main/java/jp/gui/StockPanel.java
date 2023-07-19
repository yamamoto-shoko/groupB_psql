package jp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StockPanel extends JPanel implements ActionListener{

	private JTextField textField;
	private JTable table;
	private JTextField textField_1;
	private JTable table_1;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JDialog d;
	private JDialog d2;
	private JDialog d3;
	private JDialog d4;
	private JDialog d5;
	private JDialog d6;
	AccessDB db;
	AccessDB2 db2;
	CD cd;
	OperationHistory oh;
	String[] columnNames = {"商品No","商品名","歌手名","ジャンル","金額","在庫数"};
	DefaultTableModel model = new DefaultTableModel(null, columnNames);
	DefaultTableModel model_1 = new DefaultTableModel(null, columnNames);


	/**
	 * Create the panel.
	 */
	public StockPanel() {
		setBackground(new Color(234, 248, 152));
		ButtonGroup bg = new ButtonGroup();
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(234, 248, 152));
		panel.setBounds(77, 47, 578, 435);
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("商品番号");
		lblNewLabel.setBounds(158, 40, 50, 19);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(225, 40, 96, 19);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchByItemNumber();
				}
			}
		});
		panel.add(textField);
		textField.setColumns(10);

		//検索ボタン
		JButton btnNewButton = new JButton("検索");
		btnNewButton.setBounds(358, 39, 91, 21);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand("検索");

		//検索結果テーブル
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		js.setBounds(91, 80, 420, 50);
		panel.add(js);
		rdbtnNewRadioButton_1 = new JRadioButton("出荷");
		rdbtnNewRadioButton_1.setBounds(188, 165, 76, 21);
		panel.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBackground(new Color(234, 248, 152));
		bg.add(rdbtnNewRadioButton_1);


		JLabel lblNewLabel_1 = new JLabel("件数");
		lblNewLabel_1.setBounds(158, 228, 50, 13);
		panel.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setBounds(225, 225, 96, 19);
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					registration();
				}
			}
		});
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("件");
		lblNewLabel_2.setBounds(325, 228, 33, 13);
		panel.add(lblNewLabel_2);

		//ラジオボタン
		rdbtnNewRadioButton = new JRadioButton("入荷");
		rdbtnNewRadioButton.setBounds(309, 165, 113, 21);
		panel.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBackground(new Color(234, 248, 152));
		bg.add(rdbtnNewRadioButton);

		//登録ボタン
		JButton btnNewButton_1 = new JButton("登録");
		btnNewButton_1.setBounds(358, 220, 91, 21);
		panel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(this);
		btnNewButton_1.setActionCommand("登録");

		//登録結果テーブル
		table_1 = new JTable(model_1);
		JScrollPane js2 = new JScrollPane(table_1);
		js2.setBounds(91, 266, 420, 50);
		panel.add(js2);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("検索")) {
			searchByItemNumber();

		}else if(e.getActionCommand().equals("登録")) {
			registration();
		}

	}

	private void searchByItemNumber() {
		if(!(textField.getText().matches("[0-9]+"))) {
			d2 = new JDialog();
			d2.setSize(210, 100);
			d2.setLocationRelativeTo(this);
			d2.setModal(true);
			JLabel reEnter = new JLabel("8桁の商品Noを入力してください");
			reEnter.setHorizontalAlignment(JLabel.CENTER);
			d2.getContentPane().add(reEnter);
			JButton dialogButton = new JButton("OK");
			d2.getContentPane().add(dialogButton, BorderLayout.SOUTH);
			dialogButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					d2.setVisible(false);
				}
			});
			d2.setVisible(true);

		}else {
			this.db = AccessDB.getInstance();
			int itemNum = Integer.parseInt(textField.getText());
			db.connect();
			cd = db.selectData(itemNum);
			db.disconnect();
			if(cd.getItemNum() == 0) {
				d4 = new JDialog();
				d4.setSize(160, 100);
				d4.setLocationRelativeTo(this);
				d4.setModal(true);
				JLabel selectFailed = new JLabel("データが存在しません。");
				selectFailed.setHorizontalAlignment(JLabel.CENTER);
				d4.getContentPane().add(selectFailed);
				JButton dialogButton = new JButton("OK");
				d4.getContentPane().add(dialogButton, BorderLayout.SOUTH);
				dialogButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						d4.setVisible(false);
					}
				});
				d4.setVisible(true);
			}else {
				model.setRowCount(0);
				Object[] ob = {cd.getItemNum(), cd.getItemName(), cd.getArtist(), cd.getGenre(), cd.getPrice(), cd.getStock()};
				model.addRow(ob);
			}
		}
	}

	private void registration() {
		if(!(rdbtnNewRadioButton.isSelected() || rdbtnNewRadioButton_1.isSelected()) || !(textField_1.getText().matches("[0-9]+"))) {
			d3 = new JDialog();
			d3.setSize(350, 100);
			d3.setLocationRelativeTo(this);
			d3.setModal(true);
			JLabel notCorrect = new JLabel("数値の入力、もしくは選択状態が正確ではありません。");
			notCorrect.setHorizontalAlignment(JLabel.CENTER);
			d3.getContentPane().add(notCorrect);
			JButton dialogButton = new JButton("OK");
			d3.getContentPane().add(dialogButton, BorderLayout.SOUTH);
			dialogButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					d3.setVisible(false);
				}
			});
			d3.setVisible(true);
		}else {
			int diaResult = JOptionPane.showConfirmDialog(this, "登録しても良いですか？", "確認ダイアログ", JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE);
			if(diaResult == JOptionPane.YES_OPTION) {
				int itemNum = Integer.parseInt(textField.getText());
				int inputStock = Integer.parseInt(textField_1.getText());
				int newStock = -1;
				String inventory = "";
				Date date = new Date();
				db.connect();
				cd = db.selectData(itemNum);
				db.disconnect();
				if(rdbtnNewRadioButton.isSelected()) {
					newStock = cd.getStock() + inputStock;
					inventory = "入荷";
				}else if(rdbtnNewRadioButton_1.isSelected()) {
					newStock = cd.getStock() - inputStock;
					inventory = "出荷";
				}
				if(newStock < 0) {
					d5 = new JDialog();
					d5.setSize(160, 100);
					d5.setLocationRelativeTo(this);
					d5.setModal(true);
					JLabel shortageMsg = new JLabel("在庫が不足しています。");
					shortageMsg.setHorizontalAlignment(JLabel.CENTER);
					d5.getContentPane().add(shortageMsg);
					JButton dialogButton = new JButton("OK");
					d5.getContentPane().add(dialogButton, BorderLayout.SOUTH);
					dialogButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							d5.setVisible(false);
						}
					});
					d5.setVisible(true);
				}else {
					db.connect();
					int result = db.inventoryUpdate(itemNum, newStock);
					db.disconnect();
					oh = new OperationHistory(itemNum, cd.getItemName(), inventory, inputStock, date);
					db2 = AccessDB2.getInstance();
					db2.connect();
					db2.insertData(oh);
					db2.disconnect();
					if(result > 0) {
						model_1.setRowCount(0);
						Object[] ob = {cd.getItemNum(), cd.getItemName(), cd.getArtist(), cd.getGenre(), cd.getPrice(), newStock};
						model_1.addRow(ob);
					}else {
						d6 = new JDialog();
						d6.setSize(160, 100);
						d6.setLocationRelativeTo(this);
						d6.setModal(true);
						JLabel failedaMsg = new JLabel("登録に失敗しました。");
						failedaMsg.setHorizontalAlignment(JLabel.CENTER);
						d6.getContentPane().add(failedaMsg);
						JButton dialogButton = new JButton("OK");
						d6.getContentPane().add(dialogButton, BorderLayout.SOUTH);
						dialogButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								d6.setVisible(false);
							}
						});
						d6.setVisible(true);
					}
				}
			}else if(diaResult == JOptionPane.NO_OPTION){
				d = new JDialog();
				d.setSize(150, 100);
				d.setLocationRelativeTo(this);
				d.setModal(true);
				JLabel cancel = new JLabel("中止しました。");
				cancel.setHorizontalAlignment(JLabel.CENTER);
				d.getContentPane().add(cancel);
				JButton dialogButton = new JButton("OK");
				d.getContentPane().add(dialogButton, BorderLayout.SOUTH);
				dialogButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						d.setVisible(false);
					}
				});
				d.setVisible(true);
			}
		}
	}

}