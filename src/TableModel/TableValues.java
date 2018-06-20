package TableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Database.Database;

/*
 * Class TableValues extends AbstractTableModel, lưu giá trị được hiện thị
 * của table ( Table hiển thị giá trị lấy được từ TableValues )
 * 
 * Lấy dữ liệu từ database, cài đặt định danh cột hiển thị
 */
public class TableValues extends AbstractTableModel {
	ArrayList<String[]> values = new ArrayList<String[]>();
	Database d;
	ResultSet result;
	int colCount;
	String[] colName;

	/*
	 * Lấy dữ liệu từ database đưa vào ArrayList values Biến result tr�? lần lượt
	 * đến các row của table, lấy giá trị row nạp vào values
	 */
	public TableValues(String tableName) {
		d = new Database(tableName);
		result = d.getResultSet();
		getInformationData();
		try {
			while (result.next()) {
				String[] valuesRow = new String[colCount + 1];
				for (int i = 0; i <= colCount - 1; i++) {
					valuesRow[i] = result.getString(i + 1);
					System.out.println(valuesRow[i]);
				}
				values.add(valuesRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Lấy các thông tin v�? MetaData : Số column, Tên hiển thị của các column
	 */
	public void getInformationData() {
		try {
			colCount = result.getMetaData().getColumnCount();
			colName = new String[colCount];
			for (int i = 0; i <= colCount - 1; i++) {
				colName[i] = result.getMetaData().getColumnName(i + 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Lấy số lượng column trong table
	 */
	@Override
	public int getColumnCount() {
		return colCount;
	}

	/*
	 * Lấy số lượng row trong table
	 */
	@Override
	public int getRowCount() {
		return values.size();
	}

	/*
	 * Lấy giá trị 1 vị trí trong table
	 */
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		return values.get(rowIndex)[colIndex];
	}

	/*
	 * Lấy tên các column trong table
	 */
	@Override
	public String getColumnName(int column) {
		return colName[column];
	}
}