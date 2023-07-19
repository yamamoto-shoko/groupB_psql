package jp.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileOperation {
	//※このコードの場合、それぞれ対応するfileフォルダが存在している必要があります。
	//【追記】ファイル直下生成に対応させたのでフォルダ作成する必要はなくなりました
	final String  filepath;
	public File f;
	CsvFileOperation(){
		this.filepath = "CDdata.csv";
		this.f = new File(filepath);
	}
	
	public boolean csvWriter(List<CD> list) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.f))){
			bw.write("#商品No,商品名,歌手名,ジャンル,金額,在庫数,取扱状況");
			bw.newLine();
			for(CD cd : list) {
				bw.write(cd.getItemNum() + "," + cd.getItemName() + "," 
			+ cd.getArtist() + "," + cd.getGenre() + "," + cd.getPrice() + "," + cd.getStock() + "," + cd.getHandlingStatus());
				bw.newLine();
			}
			return true;
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean csvReader(List<CD> list) {
		int itemNum;
		String itemName;
		String artist;
		String genre;
		int money;
		int stock;
		boolean handlingStatus = false;
//		int count = 0;
		if(fileExists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(this.f))){
				String line;
				while((line = br.readLine()) != null) {
					if(line.charAt(0) != '#') {
						String[] s = line.split(",");
						itemNum = Integer.parseInt(s[0]);
						itemName = s[1];
						artist = s[2];
						genre = s[3];
						money = Integer.parseInt(s[4]);
						stock = Integer.parseInt(s[5]);
						if(s[6].equals("true")) {
							handlingStatus = true;
						}
						list.add(new CD(itemNum, itemName, artist, genre, money, stock, handlingStatus));
//						count++;
					}
				}
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}else {
			return false;
		}
		
	}
	
	public boolean fileExists() {
		if(f.exists()) {
			return true;
		}else {
			return false;
		}
	}

}