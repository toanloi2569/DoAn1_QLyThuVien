package EditFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import DataFrame.Abstract_DataFrame;
import TableModel.TableDatabase;

public class Sua_EditFrame extends Abstract_EditFrame{

	public Sua_EditFrame(TableDatabase vls, Abstract_DataFrame f) {
		super(vls,f);
		// TODO Auto-generated constructor stub
		mainFrame.setTitle("Sửa dữ liệu");
		jButton.setText("Update");
		for (int i = 0; i < vls.getColumnCount(); i++) {
			GetInfoTextArea[i].setText((String) vls.getValueAt(Abstract_DataFrame.getRow, i));
			jLabels[i].setText("Thay đổi " + vls.getColumnName(i).toLowerCase() + " : ");
		}
	}

	@Override
	void setAction() {
		// TODO Auto-generated method stub
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkInfo()) {
					/* Nếu th�?a mãn đi�?u kiện sẽ được h�?i có nhập vào database không */
//					int click = JOptionPane.showConfirmDialog(null, "Chắc chắn nhập dữ liệu", "Xác nhận nhập dữ liệu",
//							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//					
//					/* Nếu đồng ý nhập, lấy dữ liệu từ text, cho vào String data, rồi truy�?n vào query */
//					if (click == JOptionPane.YES_OPTION) {
						String[] data = new String [vls.getColumnCount()];
						/* Lấy dữ liệu từ text */
						for (int i = 0; i < vls.getColumnCount(); i++) {
							if (GetInfoTextArea[i].getText().length() == 0) 
								data[i] = null;
							else data[i] = "'"+GetInfoTextArea[i].getText()+"'";	
//						}
						
						if (vls.updateSingleRow(data,Abstract_DataFrame.getRow)) f.resetTableAndInfo();
					}
				} 
				
				/* Nếu không th�?a mãn đi�?u kiện, hiện thông báo yêu cầu nhập lại */
				else {
					JOptionPane.showMessageDialog(null, "Sửa dữ liệu sai ở cột : "+mess , "", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

}