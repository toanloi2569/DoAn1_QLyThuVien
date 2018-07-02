package SoLuongSach;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Check.CheckInfoInput;
import TableModel.TableDatabase;
import TableModel.TableTemporary;

public class ThayDoiSoLuong extends Abstract_SoLuongSach{

	public ThayDoiSoLuong(String[] data, int[] rows, TableDatabase vls, TableTemporary tvls) {
		super(data, rows, vls, tvls);
		// TODO Auto-generated constructor stub
		mainButton.setText("Xác nhận sửa");
	}

	@Override
	void setupActionButton() {
		// TODO Auto-generated method stub
		mainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!isError()) {
					changeValuesInVls();
					changeValuesInTvls();
					vls.fireTableDataChanged();
					tvls.fireTableDataChanged();
				}
			}
		});
	}
	
	@Override
	void changeValuesInVls() {
		// TODO Auto-generated method stub
		for (int i = 0; i < rows.length; i++) {
			/* Tìm row tương ứng trong vls */
			int posRow = 0;
			for (int j = 0; j < vls.getRowCount(); j++) 
				if (vls.getValueAt(j, 0).equals(tvls.getValueAt(rows[i], 0))) {
					posRow = j;;
					break;
				}
			int x = Integer.parseInt((String)vls.getValueAt(posRow, 7)) + 
					Integer.parseInt((String)tvls.getValueAt(rows[i], 2)) -
					Integer.parseInt(jTextFields[i].getText());
			vls.setValueAt(x, posRow, 7 );
			System.out.println(x);
		}
	}

	@Override
	void changeValuesInTvls() {
		for (int i = 0; i < rows.length; i++) {
			tvls.setValueAt(jTextFields[i].getText(), rows[i], 2);
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
			if (Integer.parseInt(jTextFields[i].getText()) > 
					Integer.parseInt((String) vls.getValueAt(rows[i], 7)) + 
					Integer.parseInt((String) tvls.getValueAt(rows[i], 2))) {
				JOptionPane.showMessageDialog(null, "Số lượng sách mượn vượt quá số sách hiện có");
				return true;
			}
		}
		return false;
	}
}
