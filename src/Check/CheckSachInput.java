package Check;

/* Nhập vào 1 mảng string là dữ liệu
 * Kiểm tra dữ liệu có hợp lệ với bảng sách không 
 */
public class CheckSachInput {
	private String[] data;
	private String[] colName = { "Mã sách", "Tên sách", "Tên tác giả", "Nhà sản xuất", "Năm xuất bản", "Thể loại",
			"Đơn giá", "Số lượng" };
	private String mess;

	public CheckSachInput(String[] data) {
		this.data = data;
	}

	public boolean isError() {
		for (int i = 0; i < data.length; i++) {
			CheckInfoInput c = new CheckInfoInput(data[i]);
		
			/* Kiểm tra id */
			if (i == 0 && (c.isBlank() || c.isNotID())) {
				mess = colName[i].toLowerCase() + c.getMess();
				return true;
			}

			/* Kiểm tra tên sách */
			else if (i == 1 && c.isBlank()) {
				mess = colName[i] + c.getMess();
				return true;
			}

			/* Kiểm tra tên tác giả, nhà sản xuất, thể loại */
			else if ((i == 2 || i == 3 || i == 5) && !c.isBlank() && c.isNotAllWord()) {
				mess = colName[i] + c.getMess();
				return true;
			}

			/* Kiểm tra năm sản xuất */
			else if (i == 4 && !c.isBlank() && c.isNotYear()) {
				mess = colName[i] + c.getMess();
				return true;
			}

			/* Kiểm tra đơn giá */
			else if (i == 6 && !c.isBlank() && c.isNotAllNumber()) {
				mess = colName[i] + c.getMess();
				return true;
			}

			/* Kiểm tra số lượng */
			else if (i == 7 && (c.isBlank() || c.isNotAllNumber())) {
				mess = colName[i] + c.getMess();
				return true;
			}
		}
		return false;
	}

	public String getMess() {
		return mess;
	}
}
