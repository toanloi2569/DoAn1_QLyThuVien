package TableModel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Database.Database;

public class TemporaryTable extends AbstractTableModel{
	private Database d = new Database("chitietmuontra");
	private ArrayList<String[]> values = new ArrayList<String[]>(); 
	
	public TemporaryTable() {
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
	
	/* Thêm giá trị mới vào bảng. nhưng không thay đổi database */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		String[] data = new String[getColumnCount()];
		data = values.get(rowIndex);
		data[columnIndex] = aValue+"";
		values.set(rowIndex, data);
	}
	
	/* Thêm giá trị nhưng không làm thay đổi database */
	public void addSingleValue(String[] data) {
		values.add(data);
	}
	
	/* Xóa 1 dòng */
	public void deleteSingleValue(int row) {
		values.remove(row);
	}
	
	/* Xóa hết các giá trị trong bảng */
	public void deleteAllValue() {
		values.clear();
	}
}
