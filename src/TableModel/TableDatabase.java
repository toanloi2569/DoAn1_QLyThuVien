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
public class TableDatabase extends AbstractTableModel {
	private ArrayList<String[]> values = new ArrayList<String[]>();
	private Database d;
	private ResultSet result;
	private int colCount;
	private String[] colName;

	/*
	 * Lấy dữ liệu từ database đưa vào ArrayList values Biến result tr�? lần lượt
	 * đến các row của table, lấy giá trị row nạp vào values
	 */
	public TableDatabase(Database d) {
		this.d = d;
		result = d.getResultSet();
		getInformationData();
		try {
			while (result.next()) {
				String[] valuesRow = new String[colCount + 1];
				for (int i = 0; i <= colCount - 1; i++) {
					valuesRow[i] = result.getString(i + 1);
				}
				values.add(valuesRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Lấy các thông tin v�? MetaData : Số column, Tên hiển thị của các column */
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

	/* Lấy số lượng column trong table */
	@Override
	public int getColumnCount() {
		return colCount;
	}

	/* Lấy số lượng row trong table */
	@Override
	public int getRowCount() {
		return values.size();
	}

	/* Lấy giá trị 1 vị trí trong table */
	@Override
	public Object getValueAt(int rowIndex, int colIndex) {
		return values.get(rowIndex)[colIndex];
	}

	/* Lấy tên các column trong table, thêm dấu tiếng việt  */
	@Override
	public String getColumnName(int column) {
		String s = colName[column];
		if (s.equals("MaMuonTra")) return "Mã mượn trả";
		else if (s.equals("MaSach")) return "Mã sách";
		else if (s.equals("NgayTra")) return "Ngày trả (yyyy-mm-dd)";
		else if (s.equals("TienPhat")) return "Tiền phạt";
		else if (s.equals("GhiChu")) return "Ghi chú";
		else if (s.equals("MaDocGia")) return "Mã độc giả";
		else if (s.equals("TenDocGia")) return "Tên độc giả";
		else if (s.equals("NgaySinh")) return "Ngày sinh (yyyy-mm-dd)";
		else if (s.equals("GioiTinh")) return "Giới tính (Nam/Nữ)";
		else if (s.equals("QueQuan")) return "Quê quán";
		else if (s.equals("NgayMuon")) return "Ngày mượn (yyyy-mm-dd)";
		else if (s.equals("NgayHenTra")) return "Ngày hẹn trả (yyyy-mm-dd)";
		else if (s.equals("TenSach")) return "Tên sách";
		else if (s.equals("TenTacGia")) return "Tên tác giả";
		else if (s.equals("NhaSanXuat")) return "Nhà sản xuất";
		else if (s.equals("NamXuatBan")) return "Năm xuất bản";
		else if (s.equals("TheLoai")) return "Thể loại";
		else if (s.equals("DonGia")) return "Đơn giá";
		else if (s.equals("SoLuong")) return "Số lượng";
		else if (s.equals("MaNhanVien")) return "Mã nhân viên";
		else if (s.equals("TenNhanVien")) return "Tên nhân viên";
		else if (s.equals("SoLuong")) return "Số lượng";
		else return s;
	}
	
	public String[] getValuesRowAt(int row) {
		return values.get(row);
	}
	
	/* Trả ra tên cột đúng của bảng */
	public String getColumnNameDataBase(int column) {
		return colName[column];
	}
	
	/* Thêm giá trị mới vào bảng. nhưng không thay đổi database */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		String[] data = new String[getColumnCount()];
		data = getValuesRowAt(rowIndex);
		data[columnIndex] =  aValue+"";
		values.set(rowIndex, data);
	}
	
	/* Thêm 1 hàng vào database 
	 * Trả ra true nếu thành công, false nếu không thành công 
	 */
	public boolean insertSingleRow(String[] data){
		if (d.insertRow(data)) {
			for (int i = 0; i < data.length; i++) {
				if (data[i] != null) 
					data[i] = data[i].substring(1, data[i].length()-1);
			}
			values.add(data);
			return true;
		} 
		else return false;
	}
	
	/* Thay đổi giá trị 1 hàng trong database 
	 * Trả ra true nếu thành công, false nếu không thành công
	 */
	public boolean updateSingleRow(String[] data, int row){
		String pk1 = (String) getValueAt(row, 0);
		String pk2 = (String) getValueAt(row, 1);
		if (d.updateRow(pk1, pk2, data)) {
			for (int i = 0; i < data.length; i++) 
				if (data[i] != null)
					data[i] = data[i].substring(1, data[i].length()-1);
			values.set(row, data);
			return true;
		}
		else return false;
	}
	
	/* Xóa 1 hàng trong database
	 * Trả ra true nếu thành công, false nếu không thành công
	 */
	public boolean deleteSingleRow(int row){
		String pk1 = "'" + (String)getValueAt(row, 0) + "'";
		String pk2 = "'" + (String)getValueAt(row, 1) + "'";
		if (d.deleteRow(pk1, pk2)) {
			values.remove(row);
			return true;
		}
		else return false;
	}
}