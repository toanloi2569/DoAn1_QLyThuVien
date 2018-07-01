package Frame;

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
import TableModel.TableDatabase;

public class Muon_DataFrame extends Abstract_MTDataFrame{
	private static Database d = new Database("Sach");
	private static TableDatabase vls = new TableDatabase(d);
	private static JButton AddTempButton;
	private int[] rows;
	private static int stateCheck = 1;

	public Muon_DataFrame() {
		super(vls);
		AddButton_Data.setText("Xác nhận mượn sách");
		UpdateButton_Information.setText("Loại bỏ sách");
		DeleteButton_Information.setText("Làm mới");
		
		AddTempButton = new JButton("Thêm sách");
		AddTempButton.setPreferredSize(new Dimension(120, 22));
		AddTempButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		setupAddTempButtonAction();
		SearchPanel_Data.add(AddTempButton);
	}

	/* Thêm sự kiện khi nhấn nút thêm sách
	 * Có thể thêm tất cả các sách đang được chọn
	 * Duyệt rows, là danh sách hàng được chọn và thêm vào TableTemporary
	 */
	private void setupAddTempButtonAction() {	
		AddTempButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent arg0) {
				if (!isDuplicate()) {
					String [] data = new String[rows.length];
					for (int i = 0; i < rows.length; i++) {
						data[i] = "Mã sách : " + (String)vls.getValueAt(rows[i], 0) + "\n" + 
								  "Tên sách : " + (String)vls.getValueAt(rows[i], 1);
					}
					SoLuongSach s = new SoLuongSach(data);
				}
			}
			
//			mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//				@Override
//				public void valueChanged(ListSelectionEvent e) {
//					int[] rows = mainTable_Data.getSelectedRows();
//					System.out.println(rows[0]);
//					for (int i = 0; i < rows.length; i++) {
//						tvls.addValue(vls.getValuesRowAt(i));
//					}
//				}
//			});
		});
	}
	
	private boolean isDuplicate() {
		for (int i = 0; i < rows.length; i++) 
			for (int j = 0; j < vls.getRowCount(); j++) {
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

	/* Cài đặt sự kiện khi chọn 1 row trong table 
	 * Khi chọn 1 row, thông tin của nó được hiển thị trong ImformationPanel
	 */
	@Override
	void setupClickTable() {
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				rows = mainTable_Data.getSelectedRows();
				if (mainTable_Data.getSelectedRow() != -1) 
					getRow = mainTable_Data.getSelectedRow();
				/* Nếu có chọn row thì thông tin hàng đó sẽ hiện lên trên panel Information */
				setupText_Information();
			}
		});
	}

}
