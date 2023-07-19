package jp.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JPanel homePanel;
	AccessDB db;
	AccessDB2 db2;

	//------------------------------------------------------------
	JPanel bottomPanel; // 下部パネルだけフィールドで持てるようにする
	CardLayout card;
	//-----------------------------------------------------------------

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		setTitle("CD在庫管理");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		this.setResizable(false);//リサイズ禁止
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 232, 197));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "アプリケーションを終了しますか？", "終了の確認",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		setContentPane(contentPane);

		//-------

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("メニュー");
		JMenuItem menuItem1 = new JMenuItem("ホーム");
		JMenuItem menuItem2 = new JMenuItem("バックアップ");
		JMenuItem menuItem3 = new JMenuItem("復元");
		menu.add(menuItem1);
		menu.add(menuItem2);
		menu.add(menuItem3);
		menuItem1.addActionListener(e -> {
			setContentPane(homePanel);
			validate(); // パネルの再描画を行うためにvalidate()メソッドを呼び出す
		});
		menuItem2.addActionListener(e -> {
			backup();
		});
		menuItem3.addActionListener(e -> {
			restore();
		});
		menuBar.add(menu);
		setJMenuBar(menuBar);

		//----
		homePanel = new JPanel(new GridLayout(2, 2));

		// JLabelの作成
		JLabel lblNewLabel = new JLabel("CD在庫管理システム");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		homePanel.add(lblNewLabel);

		// フォントサイズの変更
		Font font = lblNewLabel.getFont();
		lblNewLabel.setFont(font.deriveFont(font.getSize() + 40.0f)); // フォントサイズを10ポイント大きくする

		homePanel.add(lblNewLabel);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());

		// Add the "開始" and "終了" buttons to the centerPanel
		JButton btnNewButton = new JButton("開始");
		btnNewButton.addActionListener(e -> {
			setContentPane(contentPane);
			validate(); // パネルの再描画を行うためにvalidate()メソッドを呼び出す
		});
		centerPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("終了");
		btnNewButton_1.addActionListener(e -> {
			int result = JOptionPane.showConfirmDialog(null, "アプリケーションを終了しますか？", "終了の確認",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				dispose();
			}
		});
		centerPanel.add(btnNewButton_1);

		homePanel.add(centerPanel);

		// Add the first GIF to the bottom left
		Image im = null;
		URL url = this.getClass().getResource("/CD.gif");
		try {
			im = this.createImage((ImageProducer) url.getContent());
		}catch(Exception ex) {
			// System.out.println("Resource Error!");
			im = null;
		}
		ImageIcon imageIcon1 = new ImageIcon(im);
		JLabel gifLabel1 = new JLabel(imageIcon1);
		JPanel bottomLeftPanel = new JPanel(new BorderLayout());
		bottomLeftPanel.add(gifLabel1, BorderLayout.WEST);
		homePanel.add(bottomLeftPanel);

		// Add the second GIF to the bottom right
		Image im2 = null;
		URL url2 = this.getClass().getResource("/クレしん4.gif");
		try {
			im2 = this.createImage((ImageProducer) url2.getContent());
		}catch(Exception ex) {
			// System.out.println("Resource Error!");
			im2 = null;
		}
		ImageIcon imageIcon2 = new ImageIcon(im2);
		JLabel gifLabel2 = new JLabel(imageIcon2);
		JPanel bottomRightPanel = new JPanel(new BorderLayout());
		bottomRightPanel.add(gifLabel2, BorderLayout.EAST);
		homePanel.add(bottomRightPanel);

		// Set margins and alignments
		centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		bottomLeftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));
		bottomRightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		setContentPane(homePanel);
		
		
		ThreadSample t=new ThreadSample();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		homePanel.setBackground(new Color(255, 0, 0));
		homePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		t.start();
		try {
			Thread.sleep(2000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	

		//-----上部パネル-------------------------------------------------
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(250, 232, 197));

		//-----下部パネル-------------------------------------------------
		bottomPanel = new JPanel();
		card = new CardLayout();
		bottomPanel.setLayout(card);
		bottomPanel.add("パネル1", new StockPanel());
		bottomPanel.add("パネル2", new InsertPanel());
		bottomPanel.add("パネル3", new ShowPanel());
		bottomPanel.add("パネル4", new EditPanel());
		bottomPanel.add("パネル5", new InformationPanel());
		//-----ここまで---------------------------------------------------

		//-----パネル全体-------------------------------------------------
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 424, Short.MAX_VALUE)
				.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bottomPanel, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		//-----ここまで---------------------------------------------------

		//-----ボタン-----------------------------------------------------
		JButton stockButton = new JButton("入荷・出荷");
		stockButton.addActionListener(this);
		stockButton.setActionCommand("入荷・出荷");
		topPanel.add(stockButton);

		JButton insertButton = new JButton("商品追加");
		insertButton.addActionListener(this);
		insertButton.setActionCommand("商品追加");
		topPanel.add(insertButton);

		JButton showButton = new JButton("データ表示");
		showButton.addActionListener(this);
		showButton.setActionCommand("データ表示");
		topPanel.add(showButton);

		JButton editButton = new JButton("商品編集");
		editButton.addActionListener(this);
		editButton.setActionCommand("商品編集");
		topPanel.add(editButton);

		JButton informationButton = new JButton("    INFO    ");
		informationButton.addActionListener(this);
		informationButton.setActionCommand("インフォメーション");
		topPanel.add(informationButton);
		//-----ここまで--------------------------------------------------
		
		//-----配置の調整-------------------------------------------------
		GroupLayout gl_homePanel = new GroupLayout(homePanel);
		homePanel.setLayout(gl_homePanel);
		gl_homePanel.setHorizontalGroup(
			gl_homePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_homePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_homePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
						.addGroup(gl_homePanel.createSequentialGroup()
							.addComponent(bottomLeftPanel, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(bottomRightPanel, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE))
						.addComponent(centerPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_homePanel.setVerticalGroup(
			gl_homePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_homePanel.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(centerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_homePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(bottomRightPanel, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
						.addComponent(bottomLeftPanel, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
					.addContainerGap())
		);
		//-----ここまで--------------------------------------------------

	}

	//-----画面遷移機能--------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("入荷・出荷")) {
			card.show(bottomPanel, "パネル1");
		} else if (e.getActionCommand().equals("商品追加")) {
			card.show(bottomPanel, "パネル2");
		} else if (e.getActionCommand().equals("データ表示")) {
			bottomPanel.add("パネル3", new ShowPanel());
			card.show(bottomPanel, "パネル3");
		} else if (e.getActionCommand().equals("商品編集")) {
			card.show(bottomPanel, "パネル4");
		} else if (e.getActionCommand().equals("インフォメーション")) {
			bottomPanel.add("パネル5", new InformationPanel());
			card.show(bottomPanel, "パネル5");
		} else if (e.getActionCommand().equals("終了")) {
			int result = JOptionPane.showConfirmDialog(null, "アプリケーションを終了しますか？", "終了の確認",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
	//-----ここまで------------------------------------------------------
	
	class ThreadSample extends Thread{


		@Override
		public void run() {
			try {
				while(true) {
					sleep(100);//1秒スリープ
					countCounter();
				}
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}

		private synchronized void countCounter() {
			Random rd=new Random();
			int a=rd.nextInt(3);
			switch(a) {
			case 0:
				//contentPane.setBackground(new Color(255, 0, 0));
				homePanel.setBackground(new Color(255, 0, 0));
				 break;
			case 1:	 
				//contentPane.setBackground(new Color(0, 0, 255));
				homePanel.setBackground(new Color(0, 0, 255));
				 break;
			case 2:	 
				//contentPane.setBackground(new Color(0, 255, 0));
				homePanel.setBackground(new Color(0, 255, 0));
				 break;
			}
		}
	}
	
	private void backup() {
		db = AccessDB.getInstance();
		db2 = AccessDB2.getInstance();
		db.connect();
		ArrayList<CD> list = db.getDBData();
		db.disconnect();

		db2.connect();
		ArrayList<OperationHistory> list2 = db2.getData();
		db2.disconnect();

		CsvFileOperation cfo = new CsvFileOperation();
		CsvFileOperation2 cfo2 = new CsvFileOperation2();
		if(cfo.csvWriter(list) && cfo2.csvWriter(list2)) {
			//System.out.println("バックアップに成功しました。");//ダイアログで出す予定
			JDialog d = new JDialog();
			d.setTitle("アラート");
			d.setSize(300, 150);
			d.setLocationRelativeTo(this);
			d.setModal(true);
			JLabel buSuccess = new JLabel("バックアップが成功しました。");
			buSuccess.setHorizontalAlignment(JLabel.CENTER);
			d.getContentPane().add(buSuccess);
			JButton dialogButton = new JButton("OK");
			d.getContentPane().add(dialogButton, BorderLayout.SOUTH);
			dialogButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					d.setVisible(false);
				}
			});
			d.setVisible(true);
		}else {
			JDialog d = new JDialog();
			d.setTitle("アラート");
			d.setSize(300, 150);
			d.setLocationRelativeTo(this);
			d.setModal(true);
			JLabel failed = new JLabel("バックアップに失敗しました。");
			failed.setHorizontalAlignment(JLabel.CENTER);
			d.getContentPane().add(failed);
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
	
	private void restore() {
		db = AccessDB.getInstance();
		CsvFileOperation cfo = new CsvFileOperation();
		ArrayList<CD> list = new ArrayList<>();
		db2 = AccessDB2.getInstance();
		CsvFileOperation2 cfo2 = new CsvFileOperation2();
		ArrayList<OperationHistory> list2 = new ArrayList<>();
		if(cfo.csvReader(list) && cfo2.csvReader(list2)) {
			db.connect();
			int result = db.remakeTable(list);
			db.disconnect();
			db2.connect();
			int result2 = db2.remakeTable(list2);
			db2.disconnect();
			if(result > 0 && result2 > 0) {
				//System.out.println("復元に成功しました。");//ダイアログで出す予定
				JDialog d = new JDialog();
				d.setTitle("アラート");
				d.setSize(300, 150);
				d.setLocationRelativeTo(this);
				d.setModal(true);
				JLabel success = new JLabel("テーブルの復元に成功しました。");
				success.setHorizontalAlignment(JLabel.CENTER);
				d.getContentPane().add(success);
				JButton dialogButton = new JButton("OK");
				d.getContentPane().add(dialogButton, BorderLayout.SOUTH);
				dialogButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						d.setVisible(false);
					}
				});
				d.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(this, "<html>復元が失敗しました。<br>テーブルが既に存在している可能性があります。</html>");
			}

		}else {
			JDialog d = new JDialog();
			d.setTitle("アラート");
			d.setSize(300, 150);
			d.setLocationRelativeTo(this);
			d.setModal(true);
			JLabel failed = new JLabel("ファイルの読み込みに失敗しました。");
			failed.setHorizontalAlignment(JLabel.CENTER);
			d.getContentPane().add(failed);
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
