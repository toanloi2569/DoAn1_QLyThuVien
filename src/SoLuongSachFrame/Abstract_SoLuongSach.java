package SoLuongSachFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Check.CheckInfoInput;
import DataFrame.Muon_DataFrame;
import TableModel.DatabaseTable;
import TableModel.TemporaryTable;;

/* Hiển thị frame nhập số lượng mượn sách */
public abstract class Abstract_SoLuongSach {
	JFrame mainFrame;
	JPanel mainPanel;
	JButton mainButton;
	JScrollPane mainScrollPanel;
	JPanel[] jPanels;
	JTextArea[] jTextAreas;
	JTextField[] jTextFields;
	String[] data;
	int[] rows;
	TemporaryTable tvls;
	DatabaseTable vls;
	
	public Abstract_SoLuongSach(String[] data, int[] rows, DatabaseTable vls, TemporaryTable tvls) {
		this.rows = rows;
		this.vls = vls;
		this.tvls = tvls;
		this.data = data;
		prepareGUI();
		setupDisplay();
		setupActionButton();
	}
	
	/* Cài đặt tương đối các đối tượng */
	private void prepareGUI() {
		mainFrame = new JFrame("Nhập số lượng từng sách");
		mainFrame.setSize(450, (data.length < 4) ? data.length*100+60 : 4*120);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainScrollPanel = new JScrollPane(mainPanel);
		mainScrollPanel.getVerticalScrollBar().setUnitIncrement(20);
		
		mainFrame.add(mainScrollPanel);
		mainFrame.setVisible(true);
	}
	
	/* Mỗi sách ứng với 1 panel, trong mỗi panel là 1 text area và 1 textFiel nhập số lượng */
	private void setupDisplay() {
		jPanels = new JPanel[data.length];
		jTextAreas = new JTextArea[data.length];
		jTextFields = new JTextField[data.length];
		Dimension dimension = new Dimension(40, 22);
		for (int i = 0; i < data.length; i++) {
			
			jPanels[i] = new JPanel();
			jPanels[i].setLayout(new BoxLayout(jPanels[i], BoxLayout.Y_AXIS));
			jPanels[i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
					BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black)));
			
			jTextAreas[i] = new JTextArea(data[i]);
			jTextAreas[i].setBackground(null);
			jTextAreas[i].setLineWrap(true);
			jTextAreas[i].setWrapStyleWord(true);
			jTextAreas[i].setEditable(false);
			
			jTextFields[i] = new JTextField();
			jTextFields[i].setPreferredSize(dimension);
			jTextFields[i].setText("1");
			JPanel j = new JPanel(new FlowLayout(FlowLayout.LEFT));
			j.add(new JLabel("Số lượng : "));
			j.add(jTextFields[i]);
			
			jPanels[i].add(jTextAreas[i]);
			jPanels[i].add(j);
			mainPanel.add(jPanels[i]);
		}
		mainButton = new JButton();
		mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(mainButton);
	}
	
	/*Cài đặt sự kiện cho Button */
	abstract void setupActionButton();
	
	/*Thay đổi giá trị bảng Tvls khi cập nhật */
	abstract void changeValuesInTvls();
	
	/*Thay đổi giá trị bảng Vls khi cập nhật */
	abstract void changeValuesInVls();
	
	/* Kiểm tra xem dữ liệu nhập vào có lỗi hoặc vượt quá khả năng cho mượn của sách không ? */
	abstract boolean isError();
}
