package EditFrame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Check.CheckInfoInput;
import DataFrame.ChiTiet_DataFrame;
import DataFrame.Main;
import DataFrame.Tra_DataFrame;
import TableModel.TableDatabase;

public class TraMT_EditFrame extends Abstract_EditFrame{
	private TableDatabase vlsMuonTra = Main.vlsMuonTra;
	private Tra_DataFrame fTra;
	private JButton AutoCal;
	private CheckInfoInput c = new CheckInfoInput("");

	public TraMT_EditFrame(TableDatabase vls, ChiTiet_DataFrame f, Tra_DataFrame fTra) {
		super(vls, f);
		this.fTra = fTra;
		mainFrame.setTitle("Trả sách");
		jButton.setText("Trả");
		for (int i = 0; i < vls.getColumnCount(); i++) 
			jLabels[i].setText(vls.getColumnName(i) + " : ");
		
		mainFrame.remove(jScrollPanes[3]);
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
		jPanel.add(jScrollPanes[3]);
		jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		jPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		AutoCal = new JButton("Tính");
		jPanel.add(AutoCal);
		mainFrame.add(jPanel, 7);
		setupAutoCalAction();
		
		
		GetInfoTextArea[0].setText((String) vls.getValueAt(f.getRow, 0));
		GetInfoTextArea[0].setEditable(false);
		GetInfoTextArea[0].setBackground(null);
		
		GetInfoTextArea[1].setText((String) vls.getValueAt(f.getRow, 1));
		GetInfoTextArea[1].setEditable(false);
		GetInfoTextArea[1].setBackground(null);
		
		GetInfoTextArea[5].setText((String) vls.getValueAt(f.getRow, 5));
		GetInfoTextArea[5].setEditable(false);
		GetInfoTextArea[5].setBackground(null);
		
		/* Lấy ngày tháng năm để tự động sinh ngày trả */
		Calendar c = Calendar.getInstance();
		String year = c.get(Calendar.YEAR) + "";

		String month = c.get(Calendar.MONTH) + 1 + "";
		if (month.length() == 1)
			month = '0' + month;

		String day = c.get(Calendar.DATE) + "";
		if (day.length() == 1)
			day = '0' + day;
		
		GetInfoTextArea[2].setText(year+"-"+month+"-"+day);
		GetInfoTextArea[3].setText(getTienPhat()+"");
	}

	/* Cài đặt sự kiện khi nhấn nút mượn sách */
	@Override
	void setAction() {
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				/* Kiểm tra ngày trả có phù hợp không */
				if (isNotDate(GetInfoTextArea[2].getText()))
					return;
				
				/* Kiểm tra tiền phạt có phù hợp không, nếu bỏ trống thì tự điền là 0 */
				c.setData(GetInfoTextArea[3].getText());
				if (c.isBlank()) 
					GetInfoTextArea[3].setText("0");
				else if (c.isNotAllNumber()) {
					JOptionPane.showMessageDialog(null, vls.getColumnName(3) + c.getMess());
					return;
				}
				
				/* Thêm vào database, đồng thời sửa lại database bảng sách */
				updateSach();
				updateChiTiet();
			}
		});
		
	}
	
	private void setupAutoCalAction(){
		AutoCal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/* Kiểm tra định dạng ngày, nếu không đúng thì nhập lại 
				 * Nếu nhỏ hơn ngày mượn thì yêu cầu nhập lại
				 * Nếu bỏ trống thì nhập lại
				 * Nếu đúng, gọi hàm tính tiền phạt
				 */
				if (!isNotDate(GetInfoTextArea[2].getText())) {
					GetInfoTextArea[3].setText(getTienPhat()+"");
				}
				
			}
		});
	}
	
	/* Kiểm tra định dạng ngày, nếu không đúng thì nhập lại 
	 * Nếu nhỏ hơn ngày mượn thì yêu cầu nhập lại
	 */
	private boolean isNotDate(String date) {
		c.setData(date);
		if (c.isNotDate()) {
			JOptionPane.showMessageDialog(null, "Không đúng định dạng ngày");
			GetInfoTextArea[2].requestFocus();
			return true;
		}
		if (date.compareTo((String)vlsMuonTra.getValueAt(fTra.getRowTra(), 2)) < 0) {
			JOptionPane.showMessageDialog(null, "Ngày trả phải sau ngày mượn");
			GetInfoTextArea[2].requestFocus();
			return true;
		}
		return false;
	}
	
	/* Tính tiền tự động cho phần tiền phạt */
	private long getTienPhat() {
		long tienphat = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date today = sdf.parse(GetInfoTextArea[2].getText());
			Date appointment = sdf.parse((String)vlsMuonTra.getValueAt(fTra.getRowTra(), 3));
			tienphat = (today.getTime() - appointment.getTime()) / (1000*60*60*24) * 1000;
			GetInfoTextArea[3].setText(tienphat+"");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tienphat <= 0) 
			return 0;
		else return tienphat;
	}
	
	/* Cập nhật bảng sách
	 * Tìm vị trí sách cần thay đổi trong bảng sách
	 * Lấy giá trị row của sách, thay đổi phần số lượng và update 
	 */
	private void updateSach() {
		String pk = (String) vls.getValueAt(f.getRow, 1);
		int posRow = Main.vlsSach.search(pk, 0);
		String [] d =  Main.vlsSach.getValuesRowAt(posRow);
		int oldNumber = Integer.parseInt(d[7]);
		int addNumber = Integer.parseInt(GetInfoTextArea[5].getText());
		d[7] = oldNumber + addNumber + "";
		for (int i = 0; i < d.length; i++) {
			if (d[i] != null)
				d[i] = "'"+ d[i] + "'";
		}
		Main.vlsSach.updateSingleRow(d,posRow);
		Main.vlsSach.fireTableDataChanged();
	}
	
	/* Cập nhật bảng chi tiết */
	private void updateChiTiet(){
		String[] d = new String[vls.getColumnCount()];
		for (int i = 0; i < d.length; i++) {
			if (GetInfoTextArea[i].getText().length() > 0)
				d[i] = "'" + GetInfoTextArea[i].getText() + "'";
		}
		vls.updateSingleRow(d, f.getRow);
		vls.fireTableDataChanged();
	}
}
