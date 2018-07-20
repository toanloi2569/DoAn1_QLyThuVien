package DataFrame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Database.Database;
import GetDataFromExcel.AbstractGetDataFromExcel;
import GetDataFromExcel.GetSachFromExcel;
import TableModel.DatabaseTable;
import TableModel.StatisticalTable;

public class Main {
	public final static int mainFrameWidth = 1300;
	public final static int mainFrameHeight = 730;
	public static JFrame mainFrame;
	public static JPanel jPanel1, jPanel2;
	public static JButton SachButton, NhanVienButton, DocGiaButton, MuonButton, TraButton, GetSachExcelButton, StatisticalButton;
	public static DatabaseTable vlsSach = new DatabaseTable(new Database("sach"));
	public static DatabaseTable vlsMuonTra = new DatabaseTable(new Database("muontra"));
	public static DatabaseTable vlsChiTiet = new DatabaseTable();
	public static DatabaseTable vlsNhanVien = new DatabaseTable(new Database("nhanvien"));
	public static DatabaseTable vlsDocGia = new DatabaseTable(new Database("docgia"));
	public static Sach_DataFrame SachFrame = new Sach_DataFrame(vlsSach);
	public static DocGia_DataFrame DocGiaFrame = new DocGia_DataFrame(vlsDocGia);
	public static NhanVien_DataFrame NhanVienFrame = new NhanVien_DataFrame(vlsNhanVien);
	public static Muon_DataFrame MuonFrame = new Muon_DataFrame(vlsSach);
	public static Tra_DataFrame TraFrame = new Tra_DataFrame(vlsMuonTra);
	public static ChiTiet_DataFrame ChiTietFrame = new ChiTiet_DataFrame(vlsChiTiet);

	public static void main(String[] args) {
		mainFrame = new JFrame("Quản lý thư viện");
		mainFrame.setSize(mainFrameWidth, mainFrameHeight);
		mainFrame.setLocation(20, 10);
		mainFrame.setResizable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
		
		prepareGUI();
		setActionButton();
		mainFrame.setVisible(true);
	}

	/*
	 * Thiết lập hiển thị frame 1 jPanel để hiện thị các nút Sách, Nhân Viên,
	 * �?ộc Giả, Mượn Trả, Chi Tiết Mượn Trả 1 jPanel để hiển thị dữ liệu
	 */
	public static void prepareGUI() {
		Dimension dimension = new Dimension(140, 60);

		SachButton = new JButton("Sách");
		SachButton.setPreferredSize(dimension);

		NhanVienButton = new JButton("Nhân Viên");
		NhanVienButton.setPreferredSize(dimension);

		DocGiaButton = new JButton("Độc Giả");
		DocGiaButton.setPreferredSize(dimension);

		MuonButton = new JButton("Mượn sách");
		MuonButton.setPreferredSize(dimension);

		TraButton = new JButton("Chính sửa và trả");
		TraButton.setPreferredSize(dimension);
		
		GetSachExcelButton = new JButton("Lấy giữ liệu từ excel");
		GetSachExcelButton.setPreferredSize(dimension);
		GetSachExcelButton.setMargin(new Insets(0, 0, 0, 0));
		
		StatisticalButton = new JButton("Thống kê");
		StatisticalButton.setPreferredSize(dimension);

		jPanel1 = new JPanel();
		jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
		jPanel1.setPreferredSize(new Dimension(mainFrameWidth, 80));
		jPanel1.add(SachButton);
		jPanel1.add(NhanVienButton);
		jPanel1.add(DocGiaButton);
		jPanel1.add(MuonButton);
		jPanel1.add(TraButton);
		jPanel1.add(GetSachExcelButton);
		jPanel1.add(StatisticalButton);

		jPanel2 = new JPanel(new FlowLayout());
		jPanel2.setPreferredSize(new Dimension(mainFrameWidth, mainFrameHeight - 150));
		jPanel2.add(SachFrame);

		mainFrame.add(jPanel1);
		mainFrame.add(jPanel2);
	}

	public static void setActionButton() {
		SachButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.removeAll();;
				jPanel2.add(SachFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});

		DocGiaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.removeAll();;
				jPanel2.add(DocGiaFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});

		NhanVienButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.removeAll();;
				jPanel2.add(NhanVienFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});

		MuonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.removeAll();;
				jPanel2.add(MuonFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});

		TraButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.removeAll();
				jPanel2.add(TraFrame);
				jPanel2.add(ChiTietFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
				vlsChiTiet.deleteAllValues();
				vlsChiTiet.fireTableDataChanged();
			}
		});
		
		GetSachExcelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GetSachFromExcel t = new GetSachFromExcel();
			}
		});
		
		StatisticalButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jPanel2.removeAll();
				jPanel2.revalidate();
				jPanel2.repaint();
				StatisticalTable vlsStatistical = new StatisticalTable(new Database("Sach"), "Tên tác giả","Số lượng đầu sách");
				JTable table = new JTable(vlsStatistical);
				JScrollPane j = new JScrollPane(table);
				jPanel2.add(j);
			}
		});
	}
}
