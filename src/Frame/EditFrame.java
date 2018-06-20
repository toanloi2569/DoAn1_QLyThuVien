package Frame;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import TableModel.*;

public class EditFrame {

	private JFrame mainFrame;
	private JTable mainTable;
	private JScrollPane tableScrollPane;

	public EditFrame() {
		// Đặt các thành phần 1 cách tương đối vào frame
		prepareGUI();

		// Lấy dữ liệu từ database đặt vào table
		setupTable();
	}

	public void prepareGUI() {
		mainFrame = new JFrame("Test");
		mainFrame.setSize(800, 300);
		mainFrame.setLocation(450, 250);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainTable = new JTable();
		tableScrollPane = new JScrollPane(mainTable);

		mainFrame.add(tableScrollPane);
		mainFrame.setVisible(true);
	}

	/*
	 * Lấy dữ liệu từ database đặt vào table : Dữ liệu được lưu trong 1 đối
	 * tượng lớp TableValues extends AbstractTableModel
	 */
	public void setupTable() {
		mainTable.setModel(new TableValues("sach"));
	}
}