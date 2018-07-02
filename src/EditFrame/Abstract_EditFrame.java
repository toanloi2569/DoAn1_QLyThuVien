package EditFrame;

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
import DataFrame.Abstract_DataFrame;
import Database.Database;
import TableModel.TableDatabase;

public abstract class Abstract_EditFrame {
	JFrame mainFrame;
	JTextField[] GetInfoTextArea;
	JButton jButton;
	JLabel[] jLabels;
	TableDatabase vls;
	Abstract_DataFrame f;
	String mess;
	public Abstract_EditFrame(TableDatabase vls, Abstract_DataFrame f) {
		this.vls = vls;
		this.f = f;

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
		JScrollPane[] jScrollPanes = new JScrollPane[vls.getColumnCount()];
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
	public boolean checkInfo() {
		mess = "";
		/* Chạy từng cột của bảng, kiểm tra đi�?u kiện đúng đối với từng cột */
		for (int i = 0; i < vls.getColumnCount(); i++) {
			String s = vls.getColumnNameDataBase(i);
			CheckInfoInput c = new CheckInfoInput(GetInfoTextArea[i].getText());
			/* Kiểm tra các trư�?ng bắt buộc phải được nhập, nếu không nhập thì yêu cầu nhập lại */
			if (s.equals("NgayTra")   || s.equals("TenDocGia") || s.equals("CMND")      || s.equals("NgayMuon")||
				s.equals("NgayMuon")  || s.equals("NgayHenTra")|| s.equals("TenSach")   || s.equals("SoLuong")||
				s.equals("MaMuonTra") || s.equals("MaSach")    || s.equals("MaDocGia")  || s.equals("MaNhanVien"))
				if (c.isBlank()) {
					
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				};
			
			/* Nếu là các trư�?ng b�? trống thì có thể cho qua */
			if (c.isBlank()) 
				continue;
			
			/* Kiểm tra các id, id phải không được b�? trống, không cách và có định dạng ID */
			else if (s.equals("MaMuonTra") || s.equals("MaSach")    || s.equals("MaDocGia") || s.equals("MaNhanVien")) {
				if (c.isNotID() || c.isSpace()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra các trư�?ng text, các trư�?ng này chỉ được nhập chữ và space */
			else if (s.equals("TenDocGia") || s.equals("QueQuan")   || s.equals("TenTacGia") ||  s.equals("NhaSanXuat")|| 
					 s.equals("TheLoai") || s.equals("TenNhanVien")) {
				if (c.isNotAllWord()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra các trư�?ng ngày tháng, phải th�?a mãn ràng buộc ngày tháng */
			else if (s.equals("NgaySinh")  || s.equals("NgayTra")   || s.equals("NgayMuon")  || s.equals("NgayHenTra")) {
				if (c.isNotDate()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra trư�?ng giới tính */
			else if (s.equals("GioiTinh")) {
				if (c.isNotSex()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra các trư�?ng chỉ được nhập số */
			else if (s.equals("CMND")      || s.equals("TienPhat")  || s.equals("DonGia")    || s.equals("SoLuong") ||
					 s.equals("NamXuatBan")) {
				if (c.isNotAllNumber()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			}
			
			/* Kiểm tra trư�?ng email */
			else if (s.equals("Email")) {
				if (c.isNotEmail()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
			};
			
			/* Kiểm tra trư�?ng năm */
			if (s.equals("NamXuatBan")) 
				if (c.isNotYear()) {
					mess += '\n'+vls.getColumnName(i)+c.getMess();
					return false;
				}
		}
		return true;
	}

	/* Cài đặt sự kiện khi nhấn thêm hoặc sửa */
	abstract void setAction();

}
