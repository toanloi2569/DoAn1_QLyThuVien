package Frame;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import TableModel.TableDatabase;
import TableModel.TableTemporary;

public abstract class Abstract_MTDataFrame extends Abstract_DataFrame {
	public JScrollPane TableScrollPanel;
	public JTable TemporaryTable;
	public TableTemporary tvls;

	public Abstract_MTDataFrame(TableDatabase vls) {
		super(vls);
		prepareMTGUI();
	}

	public void prepareMTGUI() {
		tvls = new TableTemporary();
		TemporaryTable = new JTable(tvls);
		TemporaryTable.getColumnModel().getColumn(0).setMaxWidth(80);
		TemporaryTable.getColumnModel().getColumn(2).setMaxWidth(62);
		
		TableScrollPanel = new JScrollPane(TemporaryTable);
		TableScrollPanel.setPreferredSize(new Dimension(200, 230));
		TableScrollPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
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
