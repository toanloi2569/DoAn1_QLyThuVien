package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import Frame.EditFrame;
import TableModel.TableValues;

public class Database {
	private Connection con;
	private Statement stmt;
	String tableName;

	/*
	 * Kết nối dữ liệu đến database là table được truyền vào (tableName) 
	 * Tạo 1 đối tượng statement để thực hiện truy vấn vào table
	 */
	public Database(String tableName) {
		this.tableName = tableName;
		try {
			// Tải lớp driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Tạo đối tượng Connection
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ThuVien", "root", "2569");

			// Tạo đối tượng Statement
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Output : Tham chiếu đến đối tượng ResulSet, trỏ vào row đầu tiên của table 
	 * Dùng đối tượng này để lấy giá trị lần lượt các row trong table
	 */
	public ResultSet getResultSet() {
		ResultSet result = null;
		try {
			result = stmt.executeQuery("select * from " + tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/* Thêm 1 row vào database
	 * Input : 1 mảng data[] các giá trị cần thêm và 1 bảng lưu database hiện tại
	 * Output : Thêm data[] vào database 
	 */
	public void insertRow(String[] data,TableValues vls) {
		/* Từ data, xây dựng values cần thêm */
		String s = "";
		for (int i = 0; i < vls.getColumnCount(); i++) 
			s += data[i] + ","; 
		
		/* Thêm values vào query SQL */
		try {
			stmt.executeUpdate(
				"insert into " + tableName +  
				" values (" + s.substring(0,s.length()-1) +")" 
			);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, 
					"Trùng với dữ liệu đã có\nThay đổi mã sách, mã mượn trả hoặc mã độc giả để sửa" ,
					"", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/* Thay đổi 1 row trong datavase
	 * Input : 1 mảng data[] các giá trị cần update, bảng lưu database và 1 hàng cần update 
	 * Output : Thay đổi giá trị trong database bằng giá trị trong mảng data[] 
	 */
	public void updateRow(String data[],TableValues vls, int getRow) {
		/* Xây dựng query từ data */
		String s = "update " + tableName + " set\n ";
		for (int i = 0; i < vls.getColumnCount(); i++) {
			s += vls.getColumnNameDataBase(i) + "=" + data[i]; 
			if (i < vls.getColumnCount()-1) s += ",\n"; 
			else s += "\n";
		}
		s += "where\n " + vls.getColumnNameDataBase(0) + "='" + vls.getValueAt(getRow, 0) + "' and\n"
				   		+ vls.getColumnNameDataBase(1) + "='" + vls.getValueAt(getRow, 1) + "';";
		try {
			stmt.executeUpdate(s);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, 
					"Trùng với dữ liệu đã có\nThay đổi mã sách, mã mượn trả hoặc mã độc giả để sửa" ,
					"", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/* Xóa 1 row trong database 
	 * Input : Khóa chính, bảng lưu database, hàng cần hóa
	 * Output : Xóa hàng getRow trong database
	 */
	public void deleteRow(String pk1, String pk2, TableValues vls, int getRow) {
		String s = "delete from " + tableName + "\nwhere ";
		s += vls.getColumnNameDataBase(0) + "=" + pk1 + "and\n" 
		   + vls.getColumnNameDataBase(1) + "=" + pk2 + ";";
		System.out.println(s);
		try {
			stmt.executeUpdate(s);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Đóng liên kết */
	public void close() {
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}