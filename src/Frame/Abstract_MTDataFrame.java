package Frame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

import TableModel.TableValues;

public abstract class Abstract_MTDataFrame extends Abstract_DataFrame{
	public JScrollPane TableScrollPanel;

	public Abstract_MTDataFrame(TableValues vls) {
		super(vls);
		// TODO Auto-generated constructor stub
		TableScrollPanel = new JScrollPane();
		TableScrollPanel.setPreferredSize(new Dimension(200, 100));
 		TableScrollPanel.setBorder(BorderFactory.createCompoundBorder(
 				BorderFactory.createEmptyBorder(5, 5, 5, 5), 
 				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sách mượn")));
		InformationPanel.add(TableScrollPanel, 1);
	}

	@Override
	abstract void setupTable();

	@Override
	abstract void setupAddButtonAction();

	@Override
	abstract void setupUpdateButtonAction();

	@Override
	abstract void setupDeleteButtonAction();

	@Override
	abstract void setupClickTable();

}
