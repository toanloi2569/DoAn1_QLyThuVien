package Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.Database;
import TableModel.TableValues;

public class NhanVien_DataFrame extends Abstract_DataFrame{
	private static Database d = new Database("nhanvien");
	private static TableValues vls = new TableValues(d);

	public NhanVien_DataFrame() {
		super(vls);
		AddButton_Data.setText("Thêm nhân viên mới");
		UpdateButton_Information.setText("Sửa thông tin nhân viên");
		DeleteButton_Information.setText("Xóa nhân viên");
	}

	void setupTable() {
	
	}

	/* Cài đặt sự kiện cho addButton 
	 * Gọi 1 cửa sổ để nsd thêm thông tin vào từng trường
	 */
	@Override
	void setupAddButtonAction() {
		/* Cài đặt sự kiện khi nhấn nút "Thêm", hiện 1 bảng yêu cầu điền thông tin cần thêm */
		this.AddButton_Data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Add_EditFrame a = new Add_EditFrame(vls, NhanVien_DataFrame.this);
				a.displayFrame();
			}
		});
		
	}

	/* Cài đặt sự kiện cho updateButton 
	 * Gọi 1 cửa sổ để nsd thay đỏi thông tin từng trường
	 */
	@Override
	void setupUpdateButtonAction() {
		// TODO Auto-generated method stub
		UpdateButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getRow == -1) {
					JOptionPane.showMessageDialog(null, "Chưa chọn dữ liệu để thay đổi");
					return;
				}
				Update_EditFrame a = new Update_EditFrame(vls, NhanVien_DataFrame.this);
				a.displayFrame();
			}
		});
	}

	/* Cài đặt sự kiện cho deleteButton 
	 * Khi nhấn deleteButton, row đang được chọn sẽ bị xóa
	 * Nếu không chọn row nào, hiển thị thông báo 
	 */
	@Override
	void setupDeleteButtonAction() {
		// TODO Auto-generated method stub
		DeleteButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getRow == -1) {
					JOptionPane.showMessageDialog(null, "Chưa chọn dữ liệu để xóa");
					return;
				}
				int click = JOptionPane.showConfirmDialog(null, "Chắc chắn xóa dữ liệu ?", 
						"", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (click == JOptionPane.YES_OPTION) {
					 if(vls.deleteRow(getRow)) 
						 resetTableAndInfo();
				}
				
			}
		});
	}

	/* Cài đặt sự kiện khi chọn 1 row trong table 
	 * Khi chọn 1 row, thông tin của nó được hiển thị trong ImformationPanel
	 */
	@Override
	void setupClickTable() {
		// TODO Auto-generated method stub
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (mainTable_Data.getSelectedRow() != -1) 
					getRow = mainTable_Data.getSelectedRow();
				/* Nếu có chọn row thì thông tin hàng đó sẽ hiện lên trên panel Information */
				setupText_Information();
			}
		});
	}
	
}
