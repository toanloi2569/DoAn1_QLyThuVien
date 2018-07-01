package Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private final static int mainFrameWidth = 1300;
	private final static int mainFrameHeight = 730;
	public static JFrame mainFrame;
	public static JPanel jPanel1, jPanel2;
	public static JButton SachButton,NhanVienButton,DocGiaButton,MuonButton,ChiTietButton,ChinhSuaVaTraButton;
	public static Sach_DataFrame SachFrame = new Sach_DataFrame();
	public static DocGia_DataFrame DocGiaFrame = new DocGia_DataFrame();
	public static NhanVien_DataFrame NhanVienFrame = new NhanVien_DataFrame();
	public static Muon_DataFrame MuonFrame = new Muon_DataFrame();
	public static ChinhSuaVaTra_DataFrame ChinhSuaVaTraFrame = new ChinhSuaVaTra_DataFrame();
	public static ChiTiet_DataFrame ChiTietFrame = new ChiTiet_DataFrame();

	public static void main(String[] args) {
		mainFrame = new JFrame("Quản lý thư viện");
		mainFrame.setSize(mainFrameWidth, mainFrameHeight);
		mainFrame.setLocation(20, 10);
		mainFrame.setResizable(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));;
		prepareGUI();
		setActionButton();
		mainFrame.setVisible(true);
	}

	/* Thiết lập hiển thị frame
	 * 1 jPanel để hiện thị các nút Sách, Nhân Viên, Độc Giả, Mượn Trả, Chi Tiết Mượn Trả
	 * 1 jPanel để hiển thị dữ liệu
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
		
		ChinhSuaVaTraButton = new JButton("Chính sửa và trả");
		ChinhSuaVaTraButton.setPreferredSize(dimension);
		
		ChiTietButton = new JButton("Chi Tiết Mượn");
		ChiTietButton.setPreferredSize(dimension);
		
		jPanel1 = new JPanel();
		jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT,20,20));
		jPanel1.setPreferredSize(new Dimension(mainFrameWidth, 80));
		jPanel1.add(SachButton);
		jPanel1.add(NhanVienButton);
		jPanel1.add(DocGiaButton);
		jPanel1.add(MuonButton);
		jPanel1.add(ChinhSuaVaTraButton);
		jPanel1.add(ChiTietButton);
		
		jPanel2 = new JPanel();
		jPanel2.setPreferredSize(new Dimension(mainFrameWidth, mainFrameHeight-150));
//		jPanel2.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		jPanel2.add(MuonFrame);
		
		mainFrame.add(jPanel1);
		mainFrame.add(jPanel2);
	}
	
	public static void setActionButton(){
		SachButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.remove(0);
				jPanel2.add(SachFrame);		
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});
		
		DocGiaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.remove(0);
				jPanel2.add(DocGiaFrame);	
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});
		
		NhanVienButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.remove(0);
				jPanel2.add(NhanVienFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});
		
		MuonButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.remove(0);
				jPanel2.add(MuonFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});
		
		ChiTietButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.remove(0);
				jPanel2.add(ChiTietFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});
		
		ChinhSuaVaTraButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jPanel2.remove(0);
				jPanel2.add(ChinhSuaVaTraFrame);
				jPanel2.revalidate();
				jPanel2.repaint();
			}
		});
	}
}
