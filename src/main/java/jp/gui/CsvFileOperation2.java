package jp.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CsvFileOperation2 {
	
	final String  filepath;
	public File f;
	SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	
	CsvFileOperation2(){
		this.filepath = "HistoryData.csv";
		f = new File(filepath);
	}
	
	public boolean csvWriter(List<OperationHistory> list) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(this.f))){
			bw.write("#商品No,商品名,処理,在庫変更数,操作日時");
			bw.newLine();
			for(OperationHistory oh : list) {
				bw.write(oh.getItemNum() + "," + oh.getItemName() + "," 
						+ oh.getOperation() + "," + oh.getStock() + "," + sdf.format(oh.getUpdate_date()));
				bw.newLine();
			}
			return true;
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean csvReader(List<OperationHistory> list) {
		int itemNum;
		String itemName;
		String operation;
		int changeNum;
		Date date;
		boolean b = false;
		if(fileExists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(this.f))){
				String line;
				while((line = br.readLine()) != null) {
					if(line.charAt(0) != '#') {
						String[] s = line.split(",");
						itemNum = Integer.parseInt(s[0]);
						itemName = s[1];
						operation = s[2];
						changeNum = Integer.parseInt(s[3]);
						date = sdf.parse(s[4]);
						list.add(new OperationHistory(itemNum, itemName, operation, changeNum, date));
					}
				}
				b = true;
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		return b;
	}
	
	public boolean fileExists() {
		if(f.exists()) {
			return true;
		}else {
			return false;
		}
	}

}