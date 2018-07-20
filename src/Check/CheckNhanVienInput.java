package Check;

/* Nhập vào 1 mảng string là dữ liệu
 * Kiểm tra dữ liệu có hợp lệ với bảng sách không 
 */
public class CheckNhanVienInput {
	private String[] data;
	private String[] colName = { "Mã độc giả", "Tên độc giả", "Ngày sinh (yyyy-mm-dd)", "Giới tính (Nam/Nữ)", "CMND", "Email",
			"Quê quán"};
	private String mess;

	public CheckNhanVienInput(String[] data) {
		this.data = data;
	}

	public boolean isError() {
		for (int i = 0; i < data.length; i++) {
			CheckInfoInput c = new CheckInfoInput(data[i]);
			/* Kiểm tra id */
			if (i == 0 && (c.isBlank() || c.isNotID())){
				mess = colName[i]+c.getMess();
				return true;		
			}
				
			/* Kiểm tra tên nhân viên */
			else if (i == 1 && c.isBlank() && c.isNotAllWord()){
				mess = colName[i]+c.getMess();
				return true;
			}
			
			/* Kiểm tra giới tính */
			else if (i == 3 && !c.isBlank() && c.isNotSex()){
				mess = colName[i]+c.getMess();
				return true;
			}
		}
		return false;
	}
	
	public String getMess() {
		return mess;
	}
}
