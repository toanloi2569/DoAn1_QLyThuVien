package Frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Database.Database;
import TableModel.TableValues;

/*
 *	Frame có 2 phần chính là DisplayDataPanel và InformationPanel
 * 	+ DisplayDataPanel hiển thị database, gồm 1 search_DataPanel và 1 display_DataPanel
 * 		- search_DataPanel gồm : 1 textField tìm kiếm sách, các button hiển thị và tìm kiếm 
 * 		- display_DataPanel gồm : 1 table hiển thị data, button addRow
 * 	+ Imformation hiển thị thông tin chi tiết của từng Row chọn
 */
public class EditFrame {
	private final int mainFrameWidth = 1300;
	private final int mainFrameHeight = 680;
	private final int DataPanelWidth = (int) (0.7 * mainFrameWidth);
	private JFrame mainFrame;
	private String tableName, searchName;
	private Database d;
	public static int getRow = -1;

	/* Thành phần DataPanel */
	private JPanel DataPanel;
	private JPanel searchPanel_Data, displayPanel_Data;
	private JTextField searchTextFiled_Data;
	private JButton displayAllButton_Data, searchButton_Data, advSearchButton_Data, addButton_Data;
	private JTable mainTable_Data;
	private JScrollPane tableScrollPanel_Data;
	private TableValues vls;

	/* Thành phần InformationPanel */
	private JPanel InformationPanel;
	private JTextArea textArea_Information;
	private JButton updateButton_Information, deleteButton_Information;

	/* 
	 * Constructor, 
	 * Tạo 1 editFrame Gồm tableName : hiển thị table đó trong phần
	 * displayData vls : đối tượng chứa dữ liệu table searchName : đặt giá trị
	 * cho searchName, hiển thị cho phù hợp với bảng đang xét prepareGUI : Đặt
	 * các đối tượng một cách tương đối vào frame setupTable : Lấy dữ liệu từ
	 * vls đặt vào table
	 */
	public EditFrame(String tableName) {
		this.tableName = tableName;
		d = new Database(tableName);
		vls = new TableValues(tableName, d);
		
		/* Cài đặt hiển thị các text tùy theo bảng truyền vào */
		if (tableName.equals("sach") || tableName.equals("chitietmuontra"))
			searchName = "sách";
		else
			searchName = "độc giả";

		/* Đặt các thành phần 1 cách tương đối vào frame */
		prepareGUI();

		/* Lấy dữ liệu từ database đặt vào table, hiển thị table */
		setupTable();
		
		/* Cài đặt các action */
		setupAction();
	}

	/*
	 * Đặt thành phần 1 cách tương đối vào frame Hiển thị frame addDataPanel :
	 * Thêm các thành phần vào DataPanel addInformation : Thêm các thành phần
	 * vào InformationPanel Thêm 2 panel vào frame
	 */
	public void prepareGUI() {
		/* Cài đặt hiển thị frame */
		mainFrame = new JFrame();
		mainFrame.setSize(mainFrameWidth, mainFrameHeight);
		mainFrame.setLocation(20, 50);
		mainFrame.setResizable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.X_AXIS));

		/* Thêm thành phần DataPanel vào frame */
		addDataPanel();

		/* Thành phần InformationPanel */
		addInformationPanel();

		/* Thêm 2 Panel vào frame */
		mainFrame.add(DataPanel);
		mainFrame.add(InformationPanel);
		mainFrame.setVisible(true);
	}

	/*
	 * Thêm vị trí tương đối của DataPanel Cài đặt DataPanel addSearchPanel :
	 * Thêm searchPanel vào DataPanel addDisplayPanel : Thêm displayPanel vào
	 * DataPanel
	 */
	private void addDataPanel() {
		/* Cài đặt DataPanel */
		DataPanel = new JPanel();
		DataPanel.setLayout(new BoxLayout(DataPanel, BoxLayout.Y_AXIS));
		DataPanel.setPreferredSize(new Dimension(DataPanelWidth, mainFrameHeight));
		DataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

		/* Thêm searchPanel vào DataPanel */
		addSearchPanel_Data();
		DataPanel.add(searchPanel_Data);

		/* Thêm displayPanel vào DataPanel */
		addDisplayPanel_Data();
		DataPanel.add(displayPanel_Data);
	}

	/*
	 * Thêm vị trí tương đối của DisplayPanel_Data Cài đặt hiển thị displayPanel
	 * (Hiển thị dữ liệu trong bảng) Gồm : 1 button hiển thị toàn bộ dữ liệu 1
	 * table hiển thị data 1 button để thêm dữ liệu
	 */
	private void addDisplayPanel_Data() {
		/* Cài đặt hiển thị displayPanel */
		displayPanel_Data = new JPanel();
		displayPanel_Data.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		displayPanel_Data.setLayout(new BoxLayout(displayPanel_Data, BoxLayout.Y_AXIS));
		displayPanel_Data.setAlignmentX(Component.LEFT_ALIGNMENT);

		/* button hiển thị toàn bộ dữ liệu */
		displayAllButton_Data = new JButton("Hiển thị toàn bộ dữ liệu");
		displayAllButton_Data.setPreferredSize(new Dimension(160, 22));
		displayAllButton_Data.setAlignmentX(Component.LEFT_ALIGNMENT);

		/* hiển thị table */
		mainTable_Data = new JTable();
		tableScrollPanel_Data = new JScrollPane(mainTable_Data);
		tableScrollPanel_Data.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),
				"Dữ liệu", TitledBorder.CENTER, TitledBorder.TOP));
		tableScrollPanel_Data.setAlignmentX(Component.LEFT_ALIGNMENT);

		/* button thêm dữ liệu */
		addButton_Data = new JButton("Thêm dữ liệu");

		/* add các com vào displayPanel */
		displayPanel_Data.add(displayAllButton_Data);
		displayPanel_Data.add(tableScrollPanel_Data);
		displayPanel_Data.add(addButton_Data);
	}

	/*
	 * Thêm vị trí tương đối searchPanel Gồm : 1 searchPanel1 hiển thị textField
	 * tìm kiếm và button tìm kiếm 1 button tìm kiếm nâng cao
	 */
	private void addSearchPanel_Data() {
		/* Cài đặt hiển thị searchPanel */
		searchPanel_Data = new JPanel();
		searchPanel_Data.setPreferredSize(new Dimension(DataPanelWidth, 50));
		searchPanel_Data.setBorder(BorderFactory.createEmptyBorder(0, 0, 21, 0));
		searchPanel_Data.setLayout(new BoxLayout(searchPanel_Data, BoxLayout.Y_AXIS));
		searchPanel_Data.setAlignmentX(Component.LEFT_ALIGNMENT);

		/* Cài đặt hiển thị searchPanel1 */
		JPanel searchPanel1 = new JPanel();
		searchPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, FlowLayout.CENTER, FlowLayout.CENTER));
		searchPanel1.setAlignmentX(Component.LEFT_ALIGNMENT);
		searchPanel1.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

		/* Thành phần searchPanel1 */
		{
			/* text để nhập từ khóa cần tìm kiếm */
			searchTextFiled_Data = new JTextField();
			searchTextFiled_Data.setPreferredSize(new Dimension(500, 24));

			/* button tìm kiếm từ khóa đã nhập */
			searchButton_Data = new JButton("Tìm kiếm");
			searchButton_Data.setPreferredSize(new Dimension(65, 22));
			searchButton_Data.setMargin(new Insets(0, 0, 0, 0));
		}

		/* add vào searchPanel1 */
		searchPanel1.add(new JLabel("Tìm kiếm theo tên " + searchName + " : "));
		searchPanel1.add(searchTextFiled_Data);
		searchPanel1.add(searchButton_Data);

		/* button tìm kiếm nâng cao */
		advSearchButton_Data = new JButton("Tìm kiếm nâng cao");
		advSearchButton_Data.setPreferredSize(new Dimension(165, 22));
		advSearchButton_Data.setMargin(new Insets(0, 12, 0, 12));
		advSearchButton_Data.setAlignmentX(Component.LEFT_ALIGNMENT);

		/* add com vào searchPanel */
		searchPanel_Data.add(searchPanel1);
		searchPanel_Data.add(advSearchButton_Data);
	}

	/*
	 * Lấy dữ liệu từ ValuesTable đặt vào table 
	 * Thay đổi độ rộng cột trong table cho phù hợp
	 */
	private void setupTable() {
		mainTable_Data.setModel(vls);
		tableScrollPanel_Data.setPreferredSize(new Dimension(DataPanelWidth, mainFrameHeight - 60));
		if (tableName.toLowerCase().equals("sach"))
			setupSachTable();
		else if (tableName.toLowerCase().equals("muontra"))
			setupMuonTraTable();
		else if (tableName.toLowerCase().equals("chitietmuontra"))
			setupChiTietMuonTraTable();
		else if (tableName.toLowerCase().equals("docgia"))
			setupDocGiaTable();
	}

	/* Thay đổi độ rộng cột trong bảng sách */
	private void setupSachTable() {
		mainTable_Data.getColumnModel().getColumn(1).setMinWidth(300);
		mainTable_Data.getColumnModel().getColumn(2).setMinWidth(160);
		mainTable_Data.getColumnModel().getColumn(5).setMinWidth(160);
	}

	/* Thay đổi độ rộng cột trong bảng mượn trả */
	private void setupMuonTraTable() {

	}

	/* Thay đổi độ rộng trong bảng chi tiết mượn trả */
	private void setupChiTietMuonTraTable() {

	}

	/* Thay đổi độ rộng trong bảng độc giả */
	private void setupDocGiaTable() {

	}

	/* Thêm vị trí tương đối của InformationPanel 
	 * 		Gồm : 	1 text area hiển thị thông tin row đang được chọn
	 * 				1 update button
	 * 				1 delete button
	 */
	private void addInformationPanel() {
		/* Cài đặt hiện thị InformationPanel */
		InformationPanel = new JPanel();
		InformationPanel.setLayout(new BoxLayout(InformationPanel, BoxLayout.Y_AXIS));
		InformationPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20),
				BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Thông tin chi tiết",
						TitledBorder.LEFT, TitledBorder.TOP)));
		InformationPanel.setPreferredSize(new Dimension(mainFrameWidth-DataPanelWidth-20,mainFrameHeight));

		/* Cài đặt text hiển thị thông tin, tự động xuống dòng và không bẻ chữ khi xuống dòng */
		textArea_Information = new JTextArea();
		textArea_Information.setLineWrap(true);
		textArea_Information.setWrapStyleWord(true);
		textArea_Information.setEditable(false);
		textArea_Information.setBackground(null);
		setupText_Information();
		
		/* Cài đặt 2 button sửa, xóa */
		updateButton_Information = new JButton("Sửa");
		deleteButton_Information = new JButton("Xóa");

		/* Thêm 2 button vào cùng 1 dòng, sử dụng 1 JPanel */
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(updateButton_Information);
		buttonPanel.add(deleteButton_Information);

		/* Thêm com vào InformationPanel */
		InformationPanel.add(textArea_Information);
		InformationPanel.add(buttonPanel);
	}

	/* Cài đặt hiển thị thông tin Row lên panel Information */
	private void setupText_Information() {
		/* Duyệt các cột của table để lấy tên các row, sau đó in vào text area */
		if (getRow != -1) {
			textArea_Information.setText("");;
			for (int i = 0; i < vls.getColumnCount(); i++) 
				if (vls.getValueAt(getRow, i) != null) 
					textArea_Information.append(vls.getColumnName(i) +" : "+ vls.getValueAt(getRow, i)+"\n\n");	
				else textArea_Information.append(vls.getColumnName(i) +" : "+"\n\n");
		} else 
			for (int i = 0; i < vls.getColumnCount(); i++)
				textArea_Information.append(vls.getColumnName(i) + " : \n\n");
	}
	
	/* Reset lại trạng thái của bảng và panel Information*/
	public void resetTableAndInfo() {
		vls = new TableValues(tableName, d);
		setupTable();
		setupText_Information();
	}
	
	/* Cài đặt action */
	private void setupAction(){
		/* Cài đặt sự kiện khi nhấn nút "Thêm", hiện 1 bảng yêu cầu điền thông tin cần thêm */
		addButton_Data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAndUpdate_EditFrame a = new AddAndUpdate_EditFrame(vls,tableName,d,"Thêm");
				a.displayFrame();
			}
		});
		
		/* Cài đặt sự kiện khi chọn 1 row trong table, thông tin sẽ hiện lên ở panel Information */ 
		mainTable_Data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (mainTable_Data.getSelectedRow() != -1) 
					getRow = mainTable_Data.getSelectedRow();
				/* Nếu có chọn row thì thông tin hàng đó sẽ hiện lên trên panel Information */
				setupText_Information();
			}
		});
		
		/* Cài đặt sư kiện khi ấn nút sửa */
		updateButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getRow == -1) {
					JOptionPane.showMessageDialog(null, "Chưa chọn dữ liệu để thay đổi");
					return;
				}
				AddAndUpdate_EditFrame a = new AddAndUpdate_EditFrame(vls, tableName, d, "Sửa");
				a.displayFrame();
			}
		});
		
		/* Cài đặt sự kiện khi nấn nút xóa */ 
		deleteButton_Information.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getRow == -1) {
					JOptionPane.showMessageDialog(null, "Chưa chọn dữ liệu để xóa");
					return;
				}
				int click = JOptionPane.showConfirmDialog(null, "Chắc chắn xóa dữ liệu ?", 
						"", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (click == JOptionPane.YES_OPTION) {
					d.deleteRow("'" + (String)vls.getValueAt(getRow, 0) + "'", 
							    "'" + (String)vls.getValueAt(getRow, 1) + "'", vls, getRow);
				}
				resetTableAndInfo();
			}
		});
	}
}