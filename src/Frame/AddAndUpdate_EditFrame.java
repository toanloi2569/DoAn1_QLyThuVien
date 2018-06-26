package Frame;

import java.awt.Component;
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
import javax.swing.JTextField;

import Check.CheckInfoInput;
import Database.Database;
import TableModel.TableValues;

public class AddAndUpdate_EditFrame {
	private JFrame mainFrame;
	private JTextField[] GetInfoTextArea;
	private JButton AddButton;
	private TableValues vls;
	private String mess;
	private String tableName;
	private String AddOrUpdate;
	private Database d;
	public AddAndUpdate_EditFrame(TableValues vls, String tableName, Database d,String AddOrUpdate) {
		this.AddOrUpdate = AddOrUpdate;
		this.d = d;
		this.vls = vls;

		/* Thêm vị trí tương đối trong frame */
		prepareGUI();

		/* Cài đặt sự kiện khi nhấn thêm hoặc sửa */
		setAction();
	}

	/*
	 * Thêm vị trí tương đối vào trong frame Gồm : Các text để nhập dữ liệu
	 * Button để thực hiện nhập dữ liệu
	 */
	private void prepareGUI() {
		/* Cài đặt frame hiển thị */
		mainFrame = new JFrame(AddOrUpdate+" dữ liệu");
		mainFrame.setSize(400, 50 * vls.getColumnCount() + 60 + 90);
		mainFrame.setLocation(470, 100);
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mainFrame.setResizable(false);
		mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

		/* Cài đặt hiển thị text area nhập dữ liệu */
		addTextAreas();

		/* Cài đặt hiển thị button */
		AddButton = new JButton(AddOrUpdate);
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		p.setAlignmentX(Component.LEFT_ALIGNMENT);
		p.add(AddButton);

		mainFrame.add(p);
		mainFrame.setVisible(false);
	};

	/* Thêm các ô để nhập dữ liệu vào trong Frame */
	private void addTextAreas() {
		JLabel[] jLabels = new JLabel[vls.getColumnCount()];
		JScrollPane[] jScrollPanes = new JScrollPane[vls.getColumnCount()];
		/* Thiết kế các ô để nhập dữ liệu */
		GetInfoTextArea = new JTextField[vls.getColumnCount()];
		for (int i = 0; i < vls.getColumnCount(); i++) {
			GetInfoTextArea[i] = new JTextField();
			if (AddOrUpdate.equals("Sửa")) 
				GetInfoTextArea[i].setText((String) vls.getValueAt(EditFrame.getRow, i));

			jLabels[i] = new JLabel(AddOrUpdate + " " + vls.getColumnName(i).toLowerCase() + " : ");
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
	private boolean checkInfo() {
		mess = "";
		/* Chạy từng cột của bảng, kiểm tra điều kiện đúng đối với từng cột */
		for (int i = 0; i < vls.getColumnCount(); i++) {
			String s = vls.getColumnNameDataBase(i);
			CheckInfoInput c = new CheckInfoInput(GetInfoTextArea[i].getText());
			
			/* Kiểm tra các trường bắt buộc phải được nhập, nếu không nhập thì yêu cầu nhập lại */
			if (s.equals("NgayTra")   || s.equals("TenDocGia") || s.equals("CMND")      || s.equals("NgayMuon")||
				s.equals("NgayMuon")  || s.equals("NgayHenTra")|| s.equals("TenSach")   || s.equals("SoLuong"))
				if (c.isBlank()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				};
			
			/* Kiểm tra các id, id phải không được bỏ trống, không cách và có định dạng ID */
			if (s.equals("MaMuonTra") || s.equals("MaSach")    || s.equals("MaDocGia")) {
				if (c.isBlank() || c.isNotID() || c.isSpace()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra các trường text, các trường này chỉ được nhập chữ và space */
			else if (s.equals("TenDocGia") || s.equals("QueQuan")   || s.equals("TenTacGia") ||  s.equals("NhaSanXuat")|| 
					 s.equals("TheLoai")) {
				if (c.isNotAllWord()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra các trường ngày tháng, phải thỏa mãn ràng buộc ngày tháng */
			else if (s.equals("NgaySinh")  || s.equals("NgayTra")   || s.equals("NgayMuon")  || s.equals("NgayHenTra")) {
				if (c.isNotDate()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra trường giới tính */
			else if (s.equals("GioiTinh")) {
				if (c.isNotSex()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra các trường chỉ được nhập số */
			else if (s.equals("CMND")      || s.equals("TienPhat")  || s.equals("DonGia")    || s.equals("SoLuong") ||
					 s.equals("NamXuatBan")) {
				if (c.isNotAllNumber()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra trường email */
			else if (s.equals("Email")) {
				if (c.isNotEmail()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			};
			
			/* Kiểm tra trường năm */
			if (s.equals("NamXuatBan")) 
				if (c.isNotYear()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
		}
		return true;
	}

	/* Cài đặt sự kiện khi nhấn thêm hoặc sửa */
	private void setAction() {
		AddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkInfo()) {
					/* Nếu thỏa mãn điều kiện sẽ được hỏi có nhập vào database không */
					int click = JOptionPane.showConfirmDialog(null, "Chắc chắn nhập dữ liệu", "Xác nhận nhập dữ liệu",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					/* Nếu đồng ý nhập, lấy dữ liệu từ text, cho vào String data, rồi truyền vào query */
					if (click == JOptionPane.YES_OPTION) {
						String[] data = new String [vls.getColumnCount()];
						/* Lấy dữ liệu từ text */
						for (int i = 0; i < vls.getColumnCount(); i++) {
							if (GetInfoTextArea[i].getText().length() == 0) 
								data[i] = null;
							else data[i] = "'"+GetInfoTextArea[i].getText()+"'";	
						}
						
						/* Xét theo từng trường hợp là thêm dữ liệu hay sửa dữ liệu */
						if (AddOrUpdate.equals("Thêm"))
							d.insertRow(data,vls);
						else {
							d.updateRow(data, vls, EditFrame.getRow);
						}
						Main.e.resetTableAndInfo();
					}
				} 
				
				/* Nếu không thỏa mãn điều kiện, hiện thông báo yêu cầu nhập lại */
				else {
					JOptionPane.showMessageDialog(null, AddOrUpdate+ " dữ liệu sai ở cột : "+mess , "", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	/* Hiển thị mainFrame */
	public void displayFrame() {
		mainFrame.setVisible(true);
	}
}
