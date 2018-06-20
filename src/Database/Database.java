package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}