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
	public TableValues(String tableName, Database d) {
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

	/* Lấy tên các column trong table */
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
		else return s;
	}
	
	public String getColumnNameDataBase(int column) {
		return colName[column];
	}
}