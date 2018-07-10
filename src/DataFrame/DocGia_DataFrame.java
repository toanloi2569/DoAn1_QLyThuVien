package DataFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.Database;
import EditFrame.Sua_EditFrame;
import EditFrame.Them_EditFrame;
import TableModel.TableDatabase;

public class DocGia_DataFrame extends Abstract_DataFrame{
	public DocGia_DataFrame(TableDatabase vls) {
		super(vls);
		AddButton_Data.setText("Thêm độc giả mới");
		UpdateButton_Information.setText("Sửa thông tin độc giả");
		DeleteButton_Information.setText("Xóa độc giả");
	}

	void setupTable() {
		
	}

	/* Cài đặt sự kiện cho addButton 
	 * G�?i 1 cửa sổ để nsd thêm thông tin vào từng trư�?ng
	 */
	@Override
	void setupAddButtonAction() {
		/* Cài đặt sự kiện khi nhấn nút "Thêm", hiện 1 bảng yêu cầu đi�?n thông tin cần thêm */
		this.AddButton_Data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Them_EditFrame a = new Them_EditFrame(vls,DocGia_DataFrame.this);
			}
		});
	}

	/* Cài đặt sự kiện cho updateButton 
	 * G�?i 1 cửa sổ để nsd thay đ�?i thông tin từng trư�?ng
	 */
	@Override
	void setupUpdateButtonAction() {
		// TODO Auto-generated method stub
		UpdateButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getRow == -1) {
					JOptionPane.showMessageDialog(null, "Chưa ch�?n dữ liệu để thay đổi");
					return;
				}
				Sua_EditFrame a = new Sua_EditFrame(vls, DocGia_DataFrame.this);
			}
		});
	}

	/* Cài đặt sự kiện cho deleteButton 
	 * Khi nhấn deleteButton, row đang được ch�?n sẽ bị xóa
	 * Nếu không ch�?n row nào, hiển thị thông báo 
	 */
	@Override
	void setupDeleteButtonAction() {
		// TODO Auto-generated method stub
		DeleteButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getRow == -1) {
					JOptionPane.showMessageDialog(null, "Chưa ch�?n dữ liệu để xóa");
					return;
				}
				int click = JOptionPane.showConfirmDialog(null, "Chắc chắn xóa dữ liệu ?", 
						"", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (click == JOptionPane.YES_OPTION) {
					for (int i = rows.length-1; i >= 0; i--) {
						if (vls.deleteSingleRow(rows[i]))
							resetTableAndInfo();
					}
				}
			}
		});
	}

	/* Cài đặt sự kiện khi ch�?n 1 row trong table 
	 * Khi ch�?n 1 row, thông tin của nó được hiển thị trong ImformationPanel
	 * Khi chuyển sang frame khác, getRow sẽ chuyển v�? -1, nên cần đi�?u kiện check thứ nhất
	 * Khi xóa 1 dòng, nếu xóa dòng cuối, getRow vẫn ch�?n dòng đó, nhưng thực tế đã bị xóa
	 * 		nên cần đi�?u kiện check thứ 2
	 */
	@Override
	void setupClickTable() {
		// TODO Auto-generated method stub
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (mainTable_Data.getSelectedRow() != -1 && mainTable_Data.getSelectedRow() <= vls.getRowCount()) 
					rows = mainTable_Data.getSelectedRows();
					getRow = mainTable_Data.getSelectedRow();
				/* Nếu có ch�?n row thì thông tin hàng đó sẽ hiện lên trên panel Information */
				setupText_Information();
			}
		});
	}

	@Override
	void displayDuLieu() {
		// TODO Auto-generated method stub
		
	}
	
}
