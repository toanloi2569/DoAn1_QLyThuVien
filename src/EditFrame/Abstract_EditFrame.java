package EditFrame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Check.CheckChiTietInput;
import Check.CheckDocGiaInput;
import Check.CheckInfoInput;
import Check.CheckMuonTraInput;
import Check.CheckNhanVienInput;
import Check.CheckSachInput;
import DataFrame.Abstract_DataFrame;
import Database.Database;
import TableModel.DatabaseTable;

public abstract class Abstract_EditFrame {
	JFrame mainFrame;
	JTextField[] GetInfoTextArea;
	JScrollPane[] jScrollPanes;
	JButton jButton;
	JLabel[] jLabels;
	DatabaseTable vls;
	Abstract_DataFrame f;
	String mess;
	public Abstract_EditFrame(DatabaseTable vls, Abstract_DataFrame f) {
		this.vls = vls;
		this.f = f;

		/* Thêm vị trí tương đối trong frame */
		prepareGUI();
		mainFrame.getRootPane().setDefaultButton(jButton);

		/* Cài đặt sự kiện khi nhấn thêm hoặc sửa */
		setAction();
	}

	/*
	 * Thêm vị trí tương đối vào trong frame Gồm : Các text để nhập dữ liệu
	 * Button để thực hiện nhập dữ liệu
	 */
	private void prepareGUI() {
		/* Cài đặt frame hiển thị */
		mainFrame = new JFrame();
		mainFrame.setSize(400, 50 * vls.getColumnCount() + 60 + 90);
		mainFrame.setLocation(470, 100);
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		/* Cài đặt hiển thị text area nhập dữ liệu */
		addTextAreas();

		/* Cài đặt hiển thị button */
		jButton = new JButton();
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		p.setAlignmentX(Component.LEFT_ALIGNMENT);
		p.add(jButton);

		mainFrame.add(p);
		mainFrame.setVisible(true);
	};

	/* Thêm các ô để nhập dữ liệu vào trong Frame */
	private void addTextAreas() {
		jLabels = new JLabel[vls.getColumnCount()];
		jScrollPanes = new JScrollPane[vls.getColumnCount()];
		/* Thiết kế các ô để nhập dữ liệu */
		GetInfoTextArea = new JTextField[vls.getColumnCount()];
		for (int i = 0; i < vls.getColumnCount(); i++) {
			GetInfoTextArea[i] = new JTextField();

			jLabels[i] = new JLabel();
			jLabels[i].setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
			jLabels[i].setAlignmentX(Component.LEFT_ALIGNMENT);

			jScrollPanes[i] = new JScrollPane(GetInfoTextArea[i]);
			jScrollPanes[i].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15),
					BorderFactory.createEtchedBorder()));
			jScrollPanes[i].setAlignmentX(Component.LEFT_ALIGNMENT);

			mainFrame.add(jLabels[i]);
			mainFrame.add(jScrollPanes[i]);
		}
	}

	/* 
	 * Kiểm tra dữ liệu đầu vào 
	 * Xây dựng thông báo hiển thị nếu xảy ra lỗi
	 */
	public boolean isError() {
		String[] data = new String[vls.getColumnCount()];
		boolean result = false;
		for (int i = 0; i < vls.getColumnCount(); i++) {
			if (GetInfoTextArea[i].getText().length() == 0) 
				data[i] = null;
			else data[i] = GetInfoTextArea[i].getText();	
		}
		
		System.out.println(f.getClass().getName());
		if (f.getClass().getName().equals("DataFrame.Sach_DataFrame")) {
			CheckSachInput c = new CheckSachInput(data);
			result = c.isError();
			mess = c.getMess();
			return result;
		}
		
		else if (f.getClass().getName().equals("DataFrame.NhanVien_DataFrame")) {
			CheckNhanVienInput c = new CheckNhanVienInput(data);
			result = c.isError();
			mess = c.getMess();
			return result;
		}
		
		else if (f.getClass().getName().equals("DataFrame.DocGia_DataFrame")) {
			CheckDocGiaInput c = new CheckDocGiaInput(data);
			result = c.isError();
			mess = c.getMess();
			return result;
		}
		
		else if (f.getClass().getName().equals("DataFrame.Tra_DataFrame")) {
			CheckMuonTraInput c = new CheckMuonTraInput(data);
			result = c.isError();
			mess = c.getMess();
			return result;
		}
		
		else if (f.getClass().getName().equals("DataFrame.Muon_DataFrame")) {
			CheckMuonTraInput c = new CheckMuonTraInput(data);
			result = c.isError();
			mess = c.getMess();
			return result;
		}
		
		else if (f.getClass().getName().equals("DataFrame.ChiTiet_DataFrame")) {
			CheckChiTietInput c = new CheckChiTietInput(data);
			result = c.isError();
			mess = c.getMess();
			return result;
		}
		
		return result;
	}

	/* Cài đặt sự kiện khi nhấn thêm hoặc sửa */
	abstract void setAction();

}
