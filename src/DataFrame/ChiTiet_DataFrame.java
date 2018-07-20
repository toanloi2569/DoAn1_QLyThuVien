package DataFrame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import EditFrame.TraMT_EditFrame;
import TableModel.DatabaseTable;

public class ChiTiet_DataFrame extends Abstract_DataFrame{
	public ChiTiet_DataFrame(DatabaseTable vls) {
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
		mainTable_Data.getColumnModel().getColumn(0).setPreferredWidth(80);
		mainTable_Data.getColumnModel().getColumn(1).setPreferredWidth(60);
		mainTable_Data.getColumnModel().getColumn(3).setPreferredWidth(100);
		mainTable_Data.getColumnModel().getColumn(5).setPreferredWidth(25);
		mainTable_Data.getColumnModel().getColumn(4).setPreferredWidth(240);
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
				displayDuLieu();
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
	
	/* Hiển thị chi tiết mượn trả để tiến hành trả sách */
	void displayDuLieu(){
		/* Kiểm tra có dòng nào được chọn hay không */
		if (getRow < 0) {
			JOptionPane.showMessageDialog(null, "Chưa chọn sách trả");
			return;
		}
		
		/* Kiểm tra sách đã trả hay chưa */
		if (vls.getValueAt(getRow, 2) != null) {
			JOptionPane.showMessageDialog(null, "Sách đã trả");
			return;
		}
		TraMT_EditFrame t = new TraMT_EditFrame(vls, ChiTiet_DataFrame.this, Main.TraFrame);
	}
}
