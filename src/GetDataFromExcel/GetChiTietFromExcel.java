package GetDataFromExcel;

import javax.swing.JOptionPane;

import Check.CheckChiTietInput;
import DataFrame.Main;

public class GetChiTietFromExcel extends AbstractGetDataFromExcel {
	public GetChiTietFromExcel() {
		super();
	}

	@Override
	boolean importData() {
		for (int i = 0; i < d.length; i++) {
			if (d[i] != null)
				d[i] = "'" + d[i] + "'";
		}
		if (!Main.vlsChiTiet.insertSingleRow(d)) {
			JOptionPane.showMessageDialog(null, "Dữ liệu nhập sai tại dòng : " + rowIndex
					+ "\nDữ liệu các dòng trước đã được cập nhật, nếu muốn tiếp tục cập nhật "
					+ "\nHãy sửa dòng sai, xóa các dòng trước để tiếp tục thêm phiếu mượn trả mới vào cơ sở dữ liệu");
			return false;
		}
		return true;
	}

	@Override
	boolean isError() {
		CheckChiTietInput c = new CheckChiTietInput(d);
		if (c.isError()) {
			return true;
		}
		return false;
	}

	@Override
	void updateTable() {
		Main.vlsChiTiet.fireTableDataChanged();
	}

}
