package GetDataFromExcel;

import javax.swing.JOptionPane;

import Check.CheckMuonTraInput;
import DataFrame.Main;

public class GetMuonTraFromExcel extends AbstractGetDataFromExcel {
	public GetMuonTraFromExcel() {
		super();
	}

	@Override
	boolean importData() {
		for (int i = 0; i < d.length; i++) {
			if (d[i] != null)
				d[i] = "'" + d[i] + "'";
		}
		if (!Main.vlsMuonTra.insertSingleRow(d)) {
			JOptionPane.showMessageDialog(null, "Dữ liệu nhập sai tại dòng : " + rowIndex
					+ "\nDữ liệu các dòng trước đã được cập nhật, nếu muốn tiếp tục cập nhật "
					+ "\nHãy sửa dòng sai, xóa các dòng trước để tiếp tục thêm phiếu mượn trả mới vào cơ sở dữ liệu");
			return false;
		}
		return true;
	}

	@Override
	boolean isError() {
		CheckMuonTraInput c = new CheckMuonTraInput(d);
		if (c.isError()) {
			return true;
		}
		return false;
	}

	@Override
	void updateTable() {
		Main.vlsMuonTra.fireTableDataChanged();
	}

}
