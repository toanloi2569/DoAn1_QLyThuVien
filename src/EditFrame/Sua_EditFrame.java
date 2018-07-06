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
		GetInfoTextArea[0].setEditable(false);
		GetInfoTextArea[0].setBackground(null);
	}

	@Override
	void setAction() {
		// TODO Auto-generated method stub
		jButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkInfo()) {
					/* Lấy dữ liệu từ text */
					String[] data = new String [vls.getColumnCount()];
					for (int i = 0; i < vls.getColumnCount(); i++) {
						if (GetInfoTextArea[i].getText().length() == 0) 
							data[i] = null;
						else data[i] = "'"+GetInfoTextArea[i].getText()+"'";	
					}
					
					/* Thêm vào database */
					if (vls.updateSingleRow(data,Abstract_DataFrame.getRow)) 
						f.resetTableAndInfo();
					mainFrame.setVisible(false);

				} 
				
				/* Nếu không th�?a mãn đi�?u kiện, hiện thông báo yêu cầu nhập lại */
				else {
					JOptionPane.showMessageDialog(null, "Sửa dữ liệu sai ở cột : "+mess , "", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

}
