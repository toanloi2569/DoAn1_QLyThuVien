package GetDataFromExcel;

import javax.swing.JOptionPane;

import Check.CheckDocGiaInput;
import DataFrame.Main;

public class GetDocGiaFromExcel extends AbstractGetDataFromExcel {
	public GetDocGiaFromExcel() {
		super();
	}

	@Override
	boolean importData() {
		for (int i = 0; i < d.length; i++) {
			if (d[i] != null)
				d[i] = "'" + d[i] + "'";
		}
		if (!Main.vlsDocGia.insertSingleRow(d)) {
			JOptionPane.showMessageDialog(null, "Dữ liệu nhập sai tại dòng : " + rowIndex
					+ "\nDữ liệu các dòng trước đã được cập nhật, nếu muốn tiếp tục cập nhật "
					+ "\nHãy sửa dòng sai, xóa các dòng trước để tiếp tục thêm độc giả mới vào cơ sở dữ liệu");
			return false;
		}
		return false;
	}

	@Override
	boolean isError() {
		CheckDocGiaInput c = new CheckDocGiaInput(d);
		if (c.isError()) {
			return true;
		}
		return false;
	}

	@Override
	void updateTable() {
		Main.vlsDocGia.fireTableDataChanged();
	}

}
