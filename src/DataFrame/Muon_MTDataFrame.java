package DataFrame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.Database;
import EditFrame.MuonMT_EditFrame;
import SoLuongSachFrame.Abstract_SoLuongSach;
import SoLuongSachFrame.ThayDoiSoLuong;
import SoLuongSachFrame.ThemSach;
import TableModel.TableDatabase;
import TableModel.TableTemporary;

public class Muon_MTDataFrame extends Abstract_DataFrame{
	private int[] rowsTemporary;
	private static JButton AddTempButton;
	private static JButton Update2Button_Information;
	private JScrollPane TableScrollPanel;
	private JTable TemporaryTable;

	public Muon_MTDataFrame(TableDatabase vls) {
		super(vls);
		prepareMTGUI();
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
		DisplayAllPanel_Data.add(AddTempButton,BorderLayout.LINE_END);
	}
	
	public void prepareMTGUI() {
		tvls = new TableTemporary();
		TemporaryTable = new JTable(tvls);
		TemporaryTable.getColumnModel().getColumn(0).setMaxWidth(80);
		TemporaryTable.getColumnModel().getColumn(2).setMaxWidth(62);
		
		TableScrollPanel = new JScrollPane(TemporaryTable);
		TableScrollPanel.setPreferredSize(new Dimension(200, 230));
		TableScrollPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sách mượn")));
		InformationPanel.add(TableScrollPanel, 1);
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
					rows = null;
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
		AddButton_Data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tvls.getRowCount() <= 0) {
					JOptionPane.showMessageDialog(null, "Chưa nhập dữ liệu");
					return;
				}
				MuonMT_EditFrame t = new MuonMT_EditFrame(Muon_MTDataFrame.this);
			}
		});
	}
	
	/* Xóa 1 hoặc nhiều sách */
	@Override
	void setupUpdateButtonAction() {
		UpdateButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rowsTemporary == null || rowsTemporary.length <= 0) {
					JOptionPane.showMessageDialog(null, "Chưa chọn sách để xóa");
				}
				for (int i = rowsTemporary.length-1; i >= 0; i--) {
					/* Tìm row tương ứng trong vls */
					int posRow = getPosRow(i);
					int x = Integer.parseInt((String)vls.getValueAt(posRow, 7)) + 
							Integer.parseInt((String)tvls.getValueAt(rowsTemporary[i], 2));
					vls.setValueAt(x, posRow, 7 );
					tvls.deleteSingleValue(rowsTemporary[i]);
				}
				vls.fireTableDataChanged();
				tvls.fireTableDataChanged();
			}
		});
	}

	/* Xóa tất cả giá trị trong bảng tvls */
	@Override
	void setupDeleteButtonAction() {
		DeleteButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tvls == null || tvls.getRowCount() <= 0) return;
				/* Chọn tất cả các dòng, đưa vào rowsTemporary rồi xóa từng dòng */
				selectAllTemporaryTable();
				int click = JOptionPane.showConfirmDialog(null, "Đồng ý xóa ?","",JOptionPane.YES_NO_OPTION);
				if (click == JOptionPane.YES_OPTION) {
					/* Duyệt toàn bộ, tìm row tương ứng trong vls để thay đổi */
					for (int i = tvls.getRowCount()-1; i >= 0; i--) {
						int posRow = getPosRow(i);
						int x = Integer.parseInt((String)vls.getValueAt(posRow, 7)) + 
								Integer.parseInt((String)tvls.getValueAt(rowsTemporary[i], 2));
						vls.setValueAt(x, posRow, 7 );
						tvls.deleteSingleValue(rowsTemporary[i]);
					}
					vls.fireTableDataChanged();
					tvls.fireTableDataChanged();
				}
				rowsTemporary = null;
			}
		});
	}

	/* Tìm kiếm vị trí row tương ứng với row được chọn trong bảng vls 
	 * input : vị trí row trong bảng tvls 
	 * output : vị trí row tương ứng trong bảng vls
	 */
	private int getPosRow(int i) {
		for (int j = 0; j < vls.getRowCount(); j++) 
			if (vls.getValueAt(j, 0).equals(tvls.getValueAt(rowsTemporary[i], 0))) {
				return j;
			}
		return 0;
	}
	
	private void selectAllTemporaryTable() {
		rowsTemporary = new int[tvls.getRowCount()];
		for (int i = 0; i < tvls.getRowCount(); i++) 
			rowsTemporary[i] = i;
	}
	
	/* Lấy dữ liệu bảng Temporary */
	public TableTemporary getTemporaryTable(){
		return tvls;
	}
	
	/* Lấy dữ liệu trong bảng DataTable */
	public TableDatabase getDatabaseTable() {
		return vls;
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
				rowsTemporary = null;
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
				if (mainTable_Data.getSelectedRow() != -1) {
					getRow = mainTable_Data.getSelectedRow();
					rows = mainTable_Data.getSelectedRows();
				}
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

	/* Hành động khi double click vào bảng */
	@Override
	void displayDuLieu() {
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
			rows = null;
		}
	}
}
