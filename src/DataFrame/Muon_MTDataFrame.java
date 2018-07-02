package DataFrame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.Database;
import SoLuongSach.Abstract_SoLuongSach;
import SoLuongSach.ThayDoiSoLuong;
import SoLuongSach.ThemSach;
import TableModel.TableDatabase;

public class Muon_MTDataFrame extends Abstract_MTDataFrame{
	private static Database d = new Database("Sach");
	private int[] rows, rowsTemporary;
	private static TableDatabase vls = new TableDatabase(d);
	private static JButton AddTempButton;
	private static JButton Update2Button_Information;
	private static int stateCheck = 1;

	public Muon_MTDataFrame() {
		super(vls);
		AddButton_Data.setText("Xác nhận mượn sách");
		UpdateButton_Information.setText("Loại bỏ sách");
		DeleteButton_Information.setText("Làm mới");
		setupClickTempoaryTable();
		
		AddTempButton = new JButton("Thêm sách");
		AddTempButton.setPreferredSize(new Dimension(120, 22));
		AddTempButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		setupAddTempButtonAction();
		
		Update2Button_Information = new JButton("Sửa");
		setupUpdate2ButtonAction();
		
		buttonPanel.add(Update2Button_Information);
		SearchPanel_Data.add(AddTempButton);
	}

	/* Thêm sự kiện khi nhấn nút thêm sách
	 * Có thể thêm tất cả các sách đang được ch�?n
	 * Duyệt rows, là danh sách hàng được ch�?n và thêm vào TableTemporary
	 */
	private void setupAddTempButtonAction() {	
		AddTempButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent arg0) {
				if (null == rows || rows.length <= 0 ) {
					JOptionPane.showMessageDialog(null, "Chưa chọn sách để nhập");
					return;
				}
				if (!isDuplicate()) {
					String [] data = new String[rows.length];
					for (int i = 0; i < rows.length; i++) {
						data[i] = "Mã sách : " + (String)vls.getValueAt(rows[i], 0) + "\n" + 
								  "Tên sách : " + (String)vls.getValueAt(rows[i], 1);
					}
					ThemSach t  = new ThemSach(data,rows,vls,tvls);
				}
			}
		});
	}
	
	private boolean isDuplicate() {
		for (int i = 0; i < rows.length; i++) 
			for (int j = 0; j < tvls.getRowCount(); j++) {
				if (tvls.getRowCount() != 0 && vls.getValueAt(rows[i], 0).equals(tvls.getValueAt(j, 0))) {
					int click = JOptionPane.showConfirmDialog(null, "Có sách trùng, tiếp tục thực hiện ?", "", 
							JOptionPane.YES_NO_OPTION);
					if (click == JOptionPane.YES_OPTION) return false;
					else return true;
					}
				}	
		return false;
	}

	@Override
	void setupTable() {
		mainTable_Data.getColumnModel().getColumn(1).setMinWidth(300);
		mainTable_Data.getColumnModel().getColumn(2).setMinWidth(160);
		mainTable_Data.getColumnModel().getColumn(5).setMinWidth(160);
	}

	@Override
	void setupAddButtonAction() {
		
	}

	@Override
	void setupUpdateButtonAction() {
		
	}

	@Override
	void setupDeleteButtonAction() {
		
	}
	
	/* Thêm sự kiện khi ấn update2Button, cho phép ngư�?i dùng sửa số sách mượn */
	void setupUpdate2ButtonAction() {
		Update2Button_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (null == rowsTemporary|| rowsTemporary.length <= 0) {
					JOptionPane.showMessageDialog(null, "Chưa chọn sách để sửa");
					return;
				}
				String [] data = new String[rowsTemporary.length];
				for (int i = 0; i < rowsTemporary.length; i++) {
					data[i] = "Mã sách : " + (String)tvls.getValueAt(rowsTemporary[i], 0) + "\n" + 
							  "Tên sách : " + (String)tvls.getValueAt(rowsTemporary[i], 1);
				}
				ThayDoiSoLuong t  = new ThayDoiSoLuong(data,rowsTemporary,vls,tvls);
			}
		});
	}

	/* Cài đặt sự kiện khi ch�?n 1 row trong table 
	 * Khi ch�?n 1 row, thông tin của nó được hiển thị trong ImformationPanel
	 */
	@Override
	void setupClickTable() {
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				rows = mainTable_Data.getSelectedRows();
				if (mainTable_Data.getSelectedRow() != -1) 
					getRow = mainTable_Data.getSelectedRow();
				/* Nếu có ch�?n row thì thông tin hàng đó sẽ hiện lên trên panel Information */
				setupText_Information();
			}
		});
	}
	
	/* Cài đặt sự kiện, khi chọn nhiều bảng trong temporary, rowsTemporary sẽ lưu các cột được chọn */
	void setupClickTempoaryTable(){
		TemporaryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				rowsTemporary = TemporaryTable.getSelectedRows();
			}
		});
	}

}
