package Frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* Hiển thị frame nhập số lượng mượn sách */
public class SoLuongSach {
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JButton mainButton;
	private JScrollPane mainScrollPanel;
	private JPanel[] jPanels;
	private JTextArea[] jTextAreas;
	private JTextField[] jTextFields;
	private String[] data;
	
	public SoLuongSach(String[] data) {
		this.data = data;
		prepareGUI();
		setupDisplay();
		setupActionButton();
	}
	
	/* Cài đặt tương đối các đối tượng */
	private void prepareGUI() {
		mainFrame = new JFrame("Nhập số lượng từng sách");
		mainFrame.setSize(450, (data.length < 4) ? data.length*100+30 : 4*120);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainScrollPanel = new JScrollPane(mainPanel);
		
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
		mainButton = new JButton("Thêm sách");
		mainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(mainButton);
	}
	
	/* Khi nhấn vào button, chương trình sẽ duyệt jTextField để lấy số lượng và thêm vào tvls */
	private void setupActionButton() {
		
	}
}
