package Frame;

import Database.Database;
import TableModel.TableDatabase;

public class ChinhSuaVaTra_DataFrame extends Abstract_DataFrame {
	private static Database d = new Database("muontra");
	private static TableDatabase vls = new TableDatabase(d);

	public ChinhSuaVaTra_DataFrame() {
		super(vls);
		// TODO Auto-generated constructor stub
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
