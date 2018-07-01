package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import TableModel.TableValues;

public class Database {
	private Connection con;
	private Statement stmt;
	private String tableName;

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
			result = stmt.executeQuery("select * from "+ tableName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/* Thêm 1 row vào database, trả ra true nếu thêm thành công
	 * Input : 1 mảng data[] các giá trị cần thêm và 1 bảng lưu database hiện tại
	 * Output : Thêm data[] vào database 
	 */
	public boolean  insertRow(String[] data) {
		/* Xây dựng s là chuỗi giá trị cần thêm */
		ResultSet result = getResultSet();
		String s = "";
		try {
			for (int i = 0; i < result.getMetaData().getColumnCount(); i++) 
				s += data[i] + ","; 
			stmt.executeUpdate(
				"insert into " + tableName +  
				" values (" + s.substring(0,s.length()-1) +")" 
			);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, 
					"Lỗi dữ liệu nhập vào" ,
					"", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/* Thay đổi 1 row trong database, trả ra true nếu thành công
	 * Input : 1 mảng data[] các giá trị cần update, bảng lưu database và 1 hàng cần update 
	 * Output : Thay đổi giá trị trong database bằng giá trị trong mảng data[] 
	 */
	public boolean updateRow(String pk1, String pk2, String data[]) {
		/* Xây dựng query từ data */
		String s = "update " + tableName + " set\n ";
		ResultSet result = getResultSet();
		try {
		ResultSetMetaData m = result.getMetaData();
		for (int i = 1; i <= m.getColumnCount(); i++) {
			s += m.getColumnName(i) + "=" + data[i-1]; 
			if (i < m.getColumnCount()) s += ",\n"; 
			else s += "\n";
		}
		s += "where\n " + m.getColumnName(1) + "='" + pk1 + "' and\n"
				   		+ m.getColumnName(2) + "='" + pk2 + "';";
	
			stmt.executeUpdate(s);
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, 
					"Lỗi dữ liệu nhập vào" ,
					"", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return false;
		}
	}
	
	/* Xóa 1 row trong database, trả ra true nếu thành công
	 * Input : Khóa chính, bảng lưu database, hàng cần hóa
	 * Output : Xóa hàng getRow trong database
	 */
	public boolean  deleteRow(String pk1, String pk2) {
		String s = "delete from " + tableName + "\nwhere ";
		ResultSet result = getResultSet();
		try {
			ResultSetMetaData m = result.getMetaData();
			s += m.getColumnName(1) + "=" + pk1 + "and\n" 
			   + m.getColumnName(2) + "=" + pk2 + ";";
			stmt.executeUpdate(s);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, 
					"Xóa không thành công" ,
					"", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return false;
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