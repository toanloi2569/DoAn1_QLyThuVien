package DataFrame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import EditFrame.TraMT_EditFrame;
import TableModel.TableDatabase;

public class ChiTiet_DataFrame extends Abstract_DataFrame{
	public ChiTiet_DataFrame(TableDatabase vls) {
		super(vls);
		// TODO Auto-generated constructor stub
		this.remove(InformationPanel);
		AddButton_Data.setText("Trả sách");
		TableScrollPanel_Data.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),
				"Chi tiết mượn trả", TitledBorder.CENTER, TitledBorder.TOP));
		this.setPreferredSize(new Dimension(Main.mainFrameWidth/2+100, mainFrameHeight));
	}

	@Override
	void setupTable() {
		
	}

	/* Hiển thị 1 bảng để trả sách 
	 * */
	@Override
	void setupAddButtonAction() {
		AddButton_Data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/* Hiển thị 1 bảng trả sách, trong đó mã mượn, mã sách và số lượng không được thay đổi 
				 * Tự động tính ngày hiện tại và tiền phạt
				 * Ngày trả không được để trống
				 */
				if (getRow < 0) {
					JOptionPane.showMessageDialog(null, "Chưa chọn sách trả");
					return;
				}
				TraMT_EditFrame t = new TraMT_EditFrame(vls, ChiTiet_DataFrame.this, Main.TraFrame);
			}
		});
	}

	@Override
	void setupUpdateButtonAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setupDeleteButtonAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setupClickTable() {
		// TODO Auto-generated method stub
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (mainTable_Data.getSelectedRow() != -1) 
					getRow = mainTable_Data.getSelectedRow();
			}
		});	
	}
}
