package Frame;

import Database.Database;

public class Main {
	public static String tableName = "sach";
	public static EditFrame e;
	public static void main(String[] args) {
		e = new EditFrame(tableName);
	}
}
