package Frame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import TableModel.TableValues;

public class EditFrame {
	private JFrame mainFrame;
	private JTable mainTable;
	private JScrollPane tableScrollPane;

	private JButton updateButton, addButton, editButton;

	public EditFrame(String tableName) {
		// Đặt các thành phần 1 cách tương đối vào frame
		prepareGUI();

		// Lấy dữ liệu từ database đặt vào table, hiển thị table
		setupTable(tableName);
		
		// Hiển thị button
		setupButton();
	}

	public void prepareGUI() {
		mainFrame = new JFrame("Test");
		mainFrame.setSize(1300, 600);
		mainFrame.setLocation(250, 100);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainTable = new JTable();
		tableScrollPane = new JScrollPane(mainTable);
		
		addButton = new JButton("Add");
		updateButton = new JButton("Update");
		editButton = new JButton("Edit");

		mainFrame.add(addButton);
		mainFrame.add(updateButton);
		mainFrame.add(editButton);
		mainFrame.add(tableScrollPane);
		mainFrame.setVisible(true);
	}

	/*
	 * Đặt button vào vị trí
	 */
	private void setupButton() {
		addButton.setBounds(10,10,80,40);
		editButton.setBounds(10,60,80,40);
		updateButton.setBounds(10, 110, 80, 40);
	}
	
	/*
	 * Lấy dữ liệu từ ValuesTable đặt vào table Thay đổi độ rộng cột trong table
	 * cho phù hợp
	 */
	private void setupTable(String tableName) {
		mainTable.setModel(new TableValues(tableName));
		tableScrollPane.setBounds(100,10,1100,450);
		if (tableName.toLowerCase().equals("sach"))
			setupSachTable();
		else if (tableName.toLowerCase().equals("muontra"))
			setupMuonTraTable();
		else if (tableName.toLowerCase().equals("chitietmuontra"))
			setupChiTietMuonTraTable();
	}

	/*
	 * Thay đổi độ rộng cột trong bảng sách
	 */
	private void setupSachTable() {
		mainTable.getColumnModel().getColumn(1).setMinWidth(300);
		mainTable.getColumnModel().getColumn(2).setMinWidth(160);
		mainTable.getColumnModel().getColumn(5).setMinWidth(160);
	}

	/*
	 * Thay đổi độ rộng cột trong bảng mượn trả
	 */
	private void setupMuonTraTable() {

	}

	/*
	 * Thay đổi độ rộng trong bảng chi tiết mượn trả
	 */
	private void setupChiTietMuonTraTable() {

	}
}