package EditFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JOptionPane;

import DataFrame.Main;
import DataFrame.Muon_MTDataFrame;
import TableModel.TableDatabase;
import TableModel.TableTemporary;

/* Sử dụng khi ấn "Xác nhận mượn sách" trong frame mượn sách */
public class MuonMT_EditFrame extends Them_EditFrame {

	static TableDatabase vlsMuonTra = Main.vlsMuonTra;
	static TableDatabase vlsChiTiet = Main.vlsChiTiet;
	TableDatabase vlsSach;
	TableTemporary tvls;

	public MuonMT_EditFrame(Muon_MTDataFrame f) {
		super(vlsMuonTra, f);
		this.vlsSach = f.getDatabaseTable();
		this.tvls = f.getTemporaryTable();
		GetInfoTextArea[0].setEditable(false);
		GetInfoTextArea[0].setBackground(null);

		/* Lấy ngày tháng năm để tự động sinh mã mượn trả */
		Calendar c = Calendar.getInstance();
		String year = c.get(Calendar.YEAR) + "";

		String month = c.get(Calendar.MONTH) + 1 + "";
		if (month.length() == 1);
			month = '0' + month;

		String day = c.get(Calendar.DATE) + "";
		if (day.length() == 1)
			day = '0' + day;

		String hour = c.get(Calendar.HOUR) + 1 + "";
		if (hour.length() == 1)
			hour = '0' + hour;

		String minute = c.get(Calendar.MINUTE) + "";
		if (minute.length() == 1)
			minute = '0' + minute;

		String second = c.get(Calendar.SECOND) + "";
		if (second.length() == 1)
			second = '0' + second;

		GetInfoTextArea[0].setText(year + month + day + hour + minute + second);
		GetInfoTextArea[2].setText(year + "-" + month + "-" + day);
	}

	@Override
	void setAction() {
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Kiểm tra dữ liệu nhập vào, ngày mượn phải trước ngày trả */
				if (!checkInfo()) {
					JOptionPane.showMessageDialog(null, mess);
					return;
				}

				if (GetInfoTextArea[2].getText().compareTo(GetInfoTextArea[3].getText()) > 0) {
					JOptionPane.showMessageDialog(null, "Ngày mượn phải sau ngày trả");
					return;
				}

				/* Cập nhật vào database bảng sách
				 * Cập nhật vào database bảng mượn trả 
				 * Cập nhật vào database bẳng chi tiết */
				if (updateSachDatabase() && updateMuonTraDatabase() && updateChiTietDatabase()) {
					Main.vlsSach.fireTableDataChanged();
					Main.vlsMuonTra.fireTableDataChanged();
					Main.vlsChiTiet.fireTableDataChanged();
					tvls.deleteAllValue();
					tvls.fireTableDataChanged();
					mainFrame.setVisible(false);
				} 
			}
		});
	}

	/* Cập nhật bảng chi tiết mượn trả */
	private boolean updateChiTietDatabase() {
		String[] d = new String[vlsChiTiet.getColumnCount()];
		/* Lấy dữ liệu từ text */
		for (int i = tvls.getRowCount() - 1; i >= 0; i--) {
			d[0] = "'" + GetInfoTextArea[0].getText() + "'";
			d[1] = "'" + (String) tvls.getValueAt(i, 0) + "'";
			d[5] = "'" + (String) tvls.getValueAt(i, 2) + "'";
			if (!vlsChiTiet.insertSingleRow(d)) return false;
		}
		return true;
	}

	/* Cập nhật vào database bảng mượn trả */
	private boolean updateMuonTraDatabase() {
		String[] d = new String[vlsMuonTra.getColumnCount()];
		/* Lấy dữ liệu từ text */
		for (int i = 0; i < vlsMuonTra.getColumnCount(); i++) {
			d[i] = "'" + GetInfoTextArea[i].getText() + "'";
		}
		/* Thêm vào database */
		return (vlsMuonTra.insertSingleRow(d));
	}
	
	/* Cập nhật vào database bảng sách */
	private boolean updateSachDatabase() {
		String[] d = new String[vlsSach.getColumnCount()];
		for (int i = 0; i < tvls.getRowCount(); i++) {
			int posRow = getPosRow(i);
			for (int j = 0; j < d.length; j++) {
				String s = (String) vlsSach.getValueAt(posRow, j);
				if (s != null) {
					d[j] = "\'" + s + "\'";
				} else
					d[j] = s;
			}
			if (!vlsSach.updateSingleRow(d, posRow)) 
				return false;
		}
		return true;
	}

	/*
	 * Tìm kiếm vị trí row tương ứng với row được chọn trong bảng vls input : vị
	 * trí row trong bảng tvls output : vị trí row tương ứng trong bảng vls
	 */
	private int getPosRow(int i) {
		for (int j = 0; j < vlsSach.getRowCount(); j++)
			if (vlsSach.getValueAt(j, 0).equals(tvls.getValueAt(i, 0))) {
				return j;
			}
		return 0;
	}

}
