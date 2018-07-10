package DataFrame;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.Database;
import TableModel.TableDatabase;

public class Tra_DataFrame extends Abstract_DataFrame {
	ResultSet resultSet;
	int getRow;
	public Tra_DataFrame(TableDatabase vls) {
		super(vls);
		this.remove(InformationPanel);
		AddButton_Data.setText("Xem chi tiết mượn");
		TableScrollPanel_Data.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),
				"Mượn trả", TitledBorder.CENTER, TitledBorder.TOP));
		this.setPreferredSize(new Dimension(Main.mainFrameWidth/2-150, mainFrameHeight));
	}

	@Override
	void setupTable() {
	
	}

	/*  
	 * Khi nhấn vào ,sẽ hiện chi tiết mượn trả ở bảng bên cạnh 
	 */
	@Override
	void setupAddButtonAction() {
		AddButton_Data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayDuLieu();
			}
		});
	}

	/* Lấy vị trí hàng đang được chọn */
	@Override
	void setupClickTable() {
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (mainTable_Data.getSelectedRow() != -1) 
					getRow = mainTable_Data.getSelectedRow();
			}
		});
	}
	

	/*Xóa bảng chi tiết mượn trả hiện tại
	 * Gọi hàm search để lấy result set bảng chứa các giá trị tương ứng
	 * Gọi display để in bảng ra màn hình 
	 * In kết quả ra bảng chi tiết mượn trả
	 */
	void displayDuLieu(){
		/* Kiểm tra đã chọn mã mượn trả chưa */
		if (getRow < 0) {
			JOptionPane.showMessageDialog(null, "Chưa chọn mã mượn trả để hiện thị chi tiết");
			return;
		}
		
		/* Xóa bảng chi tiết mượn trả hiện tại */
		Main.vlsChiTiet.deleteAllValues();
		Main.vlsChiTiet.fireTableDataChanged();
		
		/* Gọi hàm search để tìm giá trị tương ứng trong chi tiết mượn trả */
		resultSet = Main.vlsChiTiet.searchOnDatabase((String)vls.getValueAt(getRow, 0));
		Main.vlsChiTiet.display(resultSet);
	}
	
	@Override
	void setupUpdateButtonAction() {
		
	}

	@Override
	void setupDeleteButtonAction() {
		
	}
	
	public int getRowTra() {
		return getRow;
	}
}
