package Check;

/* Nhập vào 1 mảng string là dữ liệu
 * Kiểm tra dữ liệu có hợp lệ với bảng sách không 
 */
public class CheckMuonTraInput {
	private String[] data;
	private String[] colName = { "Mã mượn trả", "Mã độc giả", "Ngày mượn (yyyy-mm-dd)", "Ngày hẹn trả (yyyy-mm-dd)"};
	private String mess;

	public CheckMuonTraInput(String[] data) {
		this.data = data;
	}

	public boolean isError() {
		for (int i = 0; i < data.length; i++) {
			CheckInfoInput c = new CheckInfoInput(data[i]);
			/* Kiểm tra id */
			if ((i == 0 || i == 1) && (c.isBlank() || c.isNotID())){
				mess = colName[i]+c.getMess();
				return true;		
			}
				
			/* Kiểm tra ngày mượn và hẹn trả */
			else if ((i == 2  || i == 3) && (c.isBlank() || c.isNotDate())) {
				mess = colName[i]+c.getMess();
				return true;
			}
		}
		if (data[2].compareTo(data[3]) > 0) {
			mess = "Ngày mượn phải trước ngày hẹn trả";
			return true;
		}
		return false;
	}
	
	public String getMess() {
		return mess;
	}
}
