package TableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Database.Database;

public class TableTemporary extends AbstractTableModel{
	final int isDuplicate = 1;
	final int isNotDuplicate = 0;
	Database d = new Database("chitietmuontra");
	ArrayList<String[]> values = new ArrayList<String[]>(); 
	
	public TableTemporary() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		String[] s = {"Mã sách","Tên sách","Số lượng"};
		return s[column];
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return values.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		// TODO Auto-generated method stub
		return values.get(rowIndex)[colIndex];
	}
	
	public void addValue(String[] data) {
		for (int i = 0; i < data.length; i++) 
			for (int j = 0; j < values.size(); j++) {
				
			}
	}
}
