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

public class Sach_DataFrame extends Abstract_DataFrame{
	private static Database d = new Database("sach");
	private static TableDatabase vls = new TableDatabase(d);

	public Sach_DataFrame() {
		super(vls);
		AddButton_Data.setText("Thêm sách mới");
		UpdateButton_Information.setText("Sửa dữ liệu sách");
		DeleteButton_Information.setText("Xóa sách");
	}

	void setupTable() {
		mainTable_Data.getColumnModel().getColumn(1).setMinWidth(300);
		mainTable_Data.getColumnModel().getColumn(2).setMinWidth(160);
		mainTable_Data.getColumnModel().getColumn(5).setMinWidth(160);
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
				Them_EditFrame a = new Them_EditFrame(vls,Sach_DataFrame.this);
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
				Sua_EditFrame a = new Sua_EditFrame(vls,Sach_DataFrame.this);
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
					 if(vls.deleteSingleRow(getRow)) 
						 resetTableAndInfo();
				}
			}
		});
	}

	/* Cài đặt sự kiện khi ch�?n 1 row trong table 
	 * Khi ch�?n 1 row, thông tin của nó được hiển thị trong ImformationPanel
	 */
	@Override
	void setupClickTable() {
		// TODO Auto-generated method stub
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (mainTable_Data.getSelectedRow() != -1) 
					getRow = mainTable_Data.getSelectedRow();
				/* Nếu có ch�?n row thì thông tin hàng đó sẽ hiện lên trên panel Information */
				setupText_Information();
			}
		});
	}
	
}
