package TableModel;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import Database.Database;

/* Tạo 1 bảng để thống kê dữ liệu
 * Nhập vào 1 mảng các string những cột cần hiển thị, 1 string chỉ cột cần đếm
 * Thành phần
 * 1 mảng ánh xạ từ string sang chỉ số cột trong database 
 * Chỉ đọc và hiển thị các cột cần hiển thị, sử dụng lệnh sql
 */
public class StatisticalTable extends AbstractTableModel {
	HashMap<String, Integer> hashMap;
	ArrayList<String[]> values;
	Database d;
	ResultSet result;
	String[] colName;
	String colCountName = null;
	int posColCount;

	/* Constructor, trường hợp không có cột cần đếm */
	public StatisticalTable(Database d, String[] colName) {
		this.d = d;
		this.colName = colName;
		setupHashMap();
		getData();
	}

	/*
	 * Trường hợp có cột cần đếm số lượng colCountName là tên cột posColCount là
	 * vị trí cột cần đếm
	 */
	public StatisticalTable(Database d, String colName, String colCountName) {
		this.d = d;
		this.colName = new String[1];
		this.colName[0] = colName;
		this.colCountName = colCountName;
		setupHashMap();
		getDataAndCount();
	}

	/* Lấy dữ liệu từ database đẻ hiển thị */
	public void getData() {
		values = new ArrayList<>();
		result = d.getResultSet();

		/* Sinh lệnh sql */
		String sql = "Select ";
		for (int i = 0; i < colName.length; i++)
			try {
				sql += result.getMetaData().getColumnName(hashMap.get(colName[i]).intValue() + 1) + ", ";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		sql = sql.substring(0, sql.length() - 2) + " from " + d.getTableName();
		result = d.getResultSet(sql);
		int stt = 0;

		try {
			while (result.next()) {
				String[] d = new String[getColumnCount()];
				d[0] = stt + "";
				for (int i = 1; i <= colName.length; i++)
					d[i] = result.getString(i);
				values.add(d);
				stt++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Lấy dữ liệu từ database, đếm số lượng row giống nhau trong nhóm để thống
	 * kê
	 */
	public void getDataAndCount() {
		values = new ArrayList<>();
		result = d.getResultSet();

		/* Sinh lệnh sql */
		String sql = "select ";
		for (int i = 0; i < colName.length; i++)
			try {
				sql += result.getMetaData().getColumnName(hashMap.get(colName[i]).intValue() + 1) + ", "
					+	" count(*) from " + d.getTableName() + "\nGroup by "
					+ result.getMetaData().getColumnName(hashMap.get(colName[0]).intValue() + 1);
				System.out.println(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		result = d.getResultSet(sql);
		int stt = 0;

		try {
			while (result.next()) {
				String[] d = new String[getColumnCount()];
				d[0] = stt + "";
				for (int i = 1; i <= colName.length+1; i++){
					d[i] = result.getString(i);
					if (d[i] == null)  d[i] = "Thiếu";
				}
				values.add(d);
				stt++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getColumnName(int colIndex) {
		if (colIndex == 0)
			return ("STT");
		else if (colIndex <= colName.length)
			return (colName[colIndex - 1]);
		else
			return colCountName;
	}

	@Override
	public int getColumnCount() {
		if (colCountName != null)
			return colName.length + 2;
		else
			return colName.length + 1;
	}

	@Override
	public int getRowCount() {
		return values.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		return values.get(rowIndex)[colIndex];
	}

	/* Xây dựng bảng ánh xạ từ tên sang cột trong database */
	private void setupHashMap() {
		hashMap = new HashMap<>();
		if (d.getTableName().equals("Sach")) {
			hashMap.put("Mã sách", 0);
			hashMap.put("Tên sách", 1);
			hashMap.put("Tên tác giả", 2);
			hashMap.put("Nhà sản xuất", 3);
			hashMap.put("Năm xuất bản", 4);
			hashMap.put("Thể loại", 5);
			hashMap.put("Đơn giá", 6);
			hashMap.put("Số lượng", 7);
		} else if (d.getTableName().equals("DocGia")) {
			hashMap.put("Mã độc giả", 0);
			hashMap.put("Tên độc giả", 1);
			hashMap.put("Ngày sinh (yyyy-mm-dd)", 2);
			hashMap.put("Giới tính (Nam/Nữ)", 3);
			hashMap.put("CMND", 4);
			hashMap.put("Email", 5);
			hashMap.put("Quê quán", 6);
			hashMap.put("SĐT", 6);
		} else if (d.getTableName().equals("NhanVien")) {
			hashMap.put("Mã nhân viên", 0);
			hashMap.put("Tên nhân viên", 1);
			hashMap.put("Giới tính (Nam/Nữ)", 2);
		} else if (d.getTableName().equals("MuonTra")) {
			hashMap.put("Mã mượn trả", 0);
			hashMap.put("Mã độc giả", 1);
			hashMap.put("Ngày mượn (yyyy-mm-dd)", 2);
			hashMap.put("Ngày hẹn trả (yyyy-mm-dd)", 3);
		} else if (d.getTableName().equals("ChiTiet")) {
			hashMap.put("Mã mượn trả", 0);
			hashMap.put("Mã sách", 1);
			hashMap.put("Ngày trả (yyyy-mm-dd)", 2);
			hashMap.put("Tiền phạt", 3);
			hashMap.put("Ghi chú", 4);
			hashMap.put("Số lượng", 5);
		}
	}
}
