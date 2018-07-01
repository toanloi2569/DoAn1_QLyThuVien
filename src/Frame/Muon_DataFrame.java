package Frame;

import Database.Database;
import TableModel.TableValues;

public class Muon_DataFrame extends Abstract_MTDataFrame{
	private static Database d = new Database("Sach");
	private static TableValues vls = new TableValues(d);

	public Muon_DataFrame() {
		super(vls);
		AddButton_Data.setText("Mượn");
		UpdateButton_Information.setText("Loại bỏ sách");
		DeleteButton_Information.setText("Làm mới");
	}

	@Override
	void setupTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setupAddButtonAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setupUpdateButtonAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setupDeleteButtonAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setupClickTable() {
		// TODO Auto-generated method stub
		
	}

}
