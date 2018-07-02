package SoLuongSach;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Check.CheckInfoInput;
import TableModel.TableDatabase;
import TableModel.TableTemporary;

public class ThemSach extends Abstract_SoLuongSach{

	public ThemSach(String[] data, int[] rows, TableDatabase vls, TableTemporary tvls) {
		super(data, rows, vls, tvls);
		// TODO Auto-generated constructor stub
		mainButton.setText("Thêm sách");
	}

	/* Khi nhấn vào button, chương trình sẽ duyệt jTextField để lấy số lượng và thêm vào tvls */
	void setupActionButton() {
		mainButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (rows.length <= 0) {
					JOptionPane.showMessageDialog(null, "Chưa ch�?n sách để nhập");
					return;
				}
				if (!isError()) {
					changeValuesInVls();
					changeValuesInTvls();	
					tvls.fireTableDataChanged();
					vls.fireTableDataChanged();
				}
			}
		});
	}
	
	/* Thay đổi giá trị bảng Tvls nếu cập nhật */
	void changeValuesInTvls() {
		boolean Duplicate = false;
		for (int i = 0; i < rows.length; i++) {
			/* Xét trư�?ng hợp đã có sách ch�?n trước đó, duyệt lần lượt để tìm và thay đổi */
			for (int j = 0; j < tvls.getRowCount(); j++) {
				if (tvls.getRowCount() > 0 && vls.getValueAt(rows[i], 0).equals(tvls.getValueAt(j, 0))) {
					int oldNumber = Integer.parseInt(tvls.getValueAt(j, 2)+"");
					int addNumber = Integer.parseInt(jTextFields[i].getText());
					tvls.setValueAt(oldNumber+addNumber, j, 2);
					Duplicate = true;
					break;
				}
			}
			
			/* Nếu không có sách trùng, thêm sách mới vào mảng mượn tạm th�?i */
			if (!Duplicate) {
				String[] d = new String[3];
				d[0] = vls.getValueAt(rows[i], 0)+"";
				d[1] = vls.getValueAt(rows[i], 1)+"";
				d[2] = jTextFields[i].getText();
				tvls.addSingleValue(d);
			}
		}
	}
	
	/* Cập nhật bảng sách hiện có */
	void changeValuesInVls() {
		for (int i = 0; i < rows.length; i++) {
			int oldNumber = Integer.parseInt(vls.getValueAt(rows[i], 7)+"");
			int subNumber = Integer.parseInt(jTextFields[i].getText());
			vls.setValueAt(oldNumber-subNumber, rows[i], 7);
		}
	}

	@Override
	boolean isError() {
		for (int i = 0; i < rows.length; i++) {
			CheckInfoInput c = new CheckInfoInput(jTextFields[i].getText());
			/* Kiểm tra nhập vào có phải là số không */
			if (c.isNotAllNumber()) {
				JOptionPane.showMessageDialog(null, c.getMess());
				return true;
			}
			
			/* Kiểm tra có vượt quá số lượng sách hiện có không */
			if (Integer.parseInt(jTextFields[i].getText()) > Integer.parseInt((String) vls.getValueAt(rows[i], 7))) {
				JOptionPane.showMessageDialog(null, "Số lượng sách mượn vượt quá số sách hiện có");
				return true;
			}
		}
		return false;
	}
}
