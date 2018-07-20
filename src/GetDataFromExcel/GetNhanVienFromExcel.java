package GetDataFromExcel;

import javax.swing.JOptionPane;

import Check.CheckNhanVienInput;
import DataFrame.Main;

public class GetNhanVienFromExcel extends AbstractGetDataFromExcel {
	public GetNhanVienFromExcel() {
		super();
	}

	@Override
	boolean importData() {
		for (int i = 0; i < d.length; i++) {
			if (d[i] != null)
				d[i] = "'" + d[i] + "'";
		}
		if (!Main.vlsNhanVien.insertSingleRow(d)) {
			JOptionPane.showMessageDialog(null, "Dữ liệu nhập sai tại dòng : " + rowIndex
					+ "\nDữ liệu các dòng trước đã được cập nhật, nếu muốn tiếp tục cập nhật "
					+ "\nHãy sửa dòng sai, xóa các dòng trước để tiếp tục thêm nhân viên mới vào cơ sở dữ liệu");
			return false;
		}
		return true;
	}

	@Override
	boolean isError() {
		CheckNhanVienInput c = new CheckNhanVienInput(d);
		if (c.isError()) {
			return true;
		}
		return false;
	}

	@Override
	void updateTable() {
		Main.vlsNhanVien.fireTableDataChanged();	
	}

}
