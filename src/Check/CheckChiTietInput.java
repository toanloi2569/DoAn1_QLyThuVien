package Check;

/* Nhập vào 1 mảng string là dữ liệu
 * Kiểm tra dữ liệu có hợp lệ với bảng sách không 
 */
public class CheckChiTietInput {
	private String[] data;
	private String[] colName = { "Mã mượn trả", "Mã sách", "Ngày trả (yyyy-mm-dd)", "Tiền phạt", "Ghi chú", "Số lượng"};
	private String mess;

	public CheckChiTietInput(String[] data) {
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
				
			/* Kiểm tra ngày trả */
			else if (i == 2 && (c.isBlank() || c.isNotDate())) {
				mess = colName[i]+c.getMess();
				return true;
			}
			
			/* Kiểm tra tiền phạt và số lượng*/
			else if ((i == 3 || i == 5) && (c.isBlank() || c.isNotAllNumber())){
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
