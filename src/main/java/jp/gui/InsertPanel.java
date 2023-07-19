package jp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertPanel extends JPanel implements ActionListener {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton bu;
	private JComboBox<String> comboBox;
	private JPanel panel;
	private JDialog d;

	/**
	 * Create the panel.
	 */
	public InsertPanel() {
		setForeground(new Color(255, 255, 255));
		setBackground(new Color(57, 96, 55));

		panel = new JPanel();
		panel.setForeground(new Color(255, 255, 255));
		panel.setBackground(new Color(57, 96, 55));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{30, 48, 44, 171, 0};
		gbl_panel.rowHeights = new int[]{30, 19, 35, 19, 35, 19, 35, 19, 35, 21, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("●商品名");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					registration();
				}
			}
		});

		JLabel lblNewLabel_1 = new JLabel("●歌手名");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 3;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					registration();
				}
			}
		});

		JLabel lblNewLabel_2 = new JLabel("●ジャンル");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 5;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		comboBox = new JComboBox<String>(new String[]{"ジャンルを選択","J-POP","K-POP","洋楽","演歌・歌謡曲","童謡・唱歌","アニメ・ゲーム","ロック・ポップス","クラシック","ジャズ","ラップ・ヒップホップ","R&B・ソウル","ハードロック・メタル"});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 5;
		panel.add(comboBox, gbc_comboBox);

		JLabel lblNewLabel_3 = new JLabel("●金額");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 7;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.gridx = 3;
		gbc_textField_2.gridy = 7;
		panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					registration();
				}
			}
		});

		bu= new JButton("登録");
		GridBagConstraints gbc_bu = new GridBagConstraints();
		gbc_bu.gridx = 3;
		gbc_bu.gridy = 9;
		panel.add(bu, gbc_bu);
		bu.addActionListener(this);
		bu.setActionCommand("登録");
		GroupLayout groupLayout_1 = new GroupLayout(this);
		groupLayout_1.setHorizontalGroup(
				groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
						.addGap(169)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(208, Short.MAX_VALUE))
				);
		groupLayout_1.setVerticalGroup(
				groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
						.addGap(71)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(123, Short.MAX_VALUE))
				);
		setLayout(groupLayout_1);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("登録")) {
			registration();
		}
		
	}
	
	private void registration() {
		AccessDB db=AccessDB.getInstance();
		AccessDB2 db2 = AccessDB2.getInstance();
		StringBuilder sb = new StringBuilder();
		String itemname=textField.getText();
		String singer=textField_1.getText();
		String price=textField_2.getText();
		String genre = (String) comboBox.getSelectedItem();
		int count=0;

		if (itemname.isEmpty()) {
			sb.append("商品名を入力してください\n");
			count++;
		}

		if (singer.isEmpty()) {
			sb.append("歌手名を入力してください\n");
			count++;
		}

		if (genre.equals("ジャンルを選択")) {
			sb.append("ジャンルを選択してください\n");
			count++;
		}

		if (!(price.matches("[0-9]+"))||price.isEmpty()) {
			sb.append("金額に数字を入力してください");
			count++;
		}


		// ダイアログの表示

		if(count>0) {
			JOptionPane.showMessageDialog(this, sb.toString());
			count=0;
			sb=null;
			textField.setText("");
			comboBox.setSelectedIndex(0);
			textField_1.setText("");
			textField_2.setText("");
			return;					
		}
		db.connect();

		
		CD cd = new CD(db.getNewItemNum(),itemname,singer,genre,Integer.parseInt(price));
		int result = db.insertCDData(cd);
		db.disconnect();
		Date date = new Date();
		OperationHistory oh = new OperationHistory(cd.getItemNum(), cd.getItemName(), "新規登録", 0, date);
		db2.connect();
		int result2 = db2.insertData(oh);
		db2.disconnect();
		if(result > 0 && result2 > 0) {
			JOptionPane.showMessageDialog(this,cd.getItemNum()+"番に登録しました");
		}else {
			d = new JDialog();
			d.setSize(160, 100);
			d.setLocationRelativeTo(this);
			d.setModal(true);
			JLabel shortageMsg = new JLabel("登録に失敗しました。");
			shortageMsg.setHorizontalAlignment(JLabel.CENTER);
			d.getContentPane().add(shortageMsg);
			JButton dialogButton = new JButton("OK");
			d.getContentPane().add(dialogButton, BorderLayout.SOUTH);
			dialogButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					d.setVisible(false);
				}
			});
			d.setVisible(true);
		}
		textField.setText("");
		comboBox.setSelectedIndex(0);
		textField_1.setText("");
		textField_2.setText("");
	}
}
