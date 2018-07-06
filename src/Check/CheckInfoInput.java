package Check;

/* Kiểm tra dữ liệu text nhập vào có đúng với yêu cầu không 
 * Nếu không đúng, ghi lỗi ra String mess 
 */
public class CheckInfoInput {
	private String InputText;
	private String mess = "";
	private int [] arr1 = {0,1,2,3,5,6,8,9};
	private int [] m1 = {1,3,5,7,8,10,12};
	private int [] m2 = {4,6,9,11};
	
	public CheckInfoInput(String input) {
		InputText = input;
	}
	
	/* Gán giá trị mới cho đối tượng */
	public void setData(String data) {
		InputText = data;
	}
	/* Kiểm tra xâu có ký tự đặc biệt không */
	public boolean isSpecialChar() {
		for (int i = 0; i < InputText.length(); i++) {
			char c = InputText.charAt(i);
			if (   (c == '~') || (c == '`') || (c == '!') || (c == '@') || (c == '#') || (c == '$') || (c == '%')
				|| (c == '^') || (c == '&') || (c == '*') || (c == '(') || (c == ')') || (c == '_') || (c == '-')
				|| (c == '+') || (c == '=') || (c == '{') || (c == '[') || (c == ']') || (c == '}') || (c == '|')
				|| (c == '\\') || (c == ':') || (c == ';') || (c == '<') || (c == ',') || (c == '>') || (c == '.')
				|| (c == '?') || (c == '/') || (c == '"') || (c == '\'')
				) {
				mess = " : Có ký tự đặc biệt";
				return true;
			}
		}
		return false;
	}

	/* Kiểm tra xâu có trống không */
	public boolean isBlank() {
		int x = InputText.length();
		if (x == 0) {
			mess = " : Dòng bỏ trắng";
			return true;
		}
		while (x > 0 && InputText.charAt(x - 1) == ' ')
			x--;
		if (x == 0) {
			mess = " : Dòng bỏ trắng";
			return true;
		}
		return false;
	}

	/* Kiểm tra có toàn là số không không */
	public boolean isNotAllNumber() {
		for (int i = 0; i < InputText.length(); i++) {
			char c = InputText.charAt(i);
			if (c < '0' || c > '9'){
				mess = " : Có ký tự không phải là số";
				return true;
			}
		}
		return false;
	}

	/* Kiểm tra có toàn là chữ không 
	 * Bao gồm cả chữ có dấu, unicode 
	 */
	public boolean isNotAllWord() {
		for (int i = 0; i < InputText.length(); i++) {
			char c = InputText.charAt(i);
			if (   (c == '~') || (c == '`') || (c == '!') || (c == '@') || (c == '#') || (c == '$') || (c == '%')
				|| (c == '^') || (c == '&') || (c == '*') || (c == '(') || (c == ')') || (c == '_') 
				|| (c == '+') || (c == '=') || (c == '{') || (c == '[') || (c == ']') || (c == '}') || (c == '|')
				|| (c == '\\') || (c == ':') || (c == ';') || (c == '<') || (c == '>') || (c == '.')
				|| (c == '?') || (c == '/') || (c == '"') || (c == '\'') || (c == '1') || (c == '2') || (c == '3')
				|| (c == '4') || (c == '5') || (c == '6') || (c == '7') || (c == '8') || (c == '9') || (c == '0')
				) {
				mess = " : Có ký tự không phải là chữ (có thể sử dụng dấu \"-\" hoặc \",\"";
				return true;
			}
		}
		return false;
	}
	
	/* 
	 * Kiểm tra có là ngày tháng hay không 
	 * Định dạng ngày yyyy-mm-dd
	 * Kiểm tra ngày, tháng, năm có tồn tại không
	 */
	public boolean isNotDate() {
		/* Kiểm tra định dạng ngày tháng năm */
		if (InputText.length() != 10) {
			mess = " : Không đúng định dạng ngày tháng năm";
			return true;
		}
		for (int i = 0; i < arr1.length; i++)
			if (InputText.charAt(arr1[i]) < '0' || InputText.charAt(arr1[i]) > '9') {
				mess = " : Không đúng định dạng ngày tháng năm";
				return true;
			}
		if (InputText.charAt(4) != '-' || InputText.charAt(7) != '-')  {
			mess = " : Không đúng định dạng ngày tháng năm";
			return true;
		}
		
		/* Giới hạn ngày, tháng, năm */
		Integer year,month,day;
		year =  Integer.parseInt(InputText.substring(0, 4));
		month = Integer.parseInt(InputText.substring(5, 7));
		day = Integer.parseInt(InputText.substring(8));
		/* Kiểm tra năm */
		if (year > 9999) {
			mess = " : 1 năm có nhiều nhất 4 chữ số";
			return true;
		}
		
		/* Kiểm tra tháng */
		if (month > 12 || month < 0) {
			mess = " : Tháng không tồn tại";
			return true;
		}
		
		/* Kiểm tra ngày */
		for (int i = 0; i < m1.length; i++) {
			if (month == m1[i] && (day > 31 || day < 1) ) {
				mess = " : Ngày không tồn tại";
				return true;
			}
		}
		for (int i = 0; i < m2.length; i++) {
			if (month == m2[i] && (day > 30 || day < 1)) {
				mess = " : Ngày không tồn tại";
				return true;
			}
		}
		if (month == 2 && year%4 == 0 && (day > 28 || day < 1)) {
			mess = " : Ngày không tồn tại";
			return true;
		}
		if (month == 2 && (day > 29 || day < 1)) {
			mess = " : Ngày không tồn tại";
			return true;
		}
		return false;
	}
	
	/* Kiểm tra có là giới tính không */
	public boolean isNotSex() {
		if (InputText.toLowerCase().equals("nam") || InputText.equals("nữ")) {
			mess = " : Không đúng định dạng giới tính, định dạng giới tính : Nam, Nữ";
			return false;
		}
		return true;
	}
	
	/* Kiểm tra có là email hay không */
	public boolean isNotEmail() {
		for (int i = 0; i < InputText.length(); i++) {
			char c = InputText.charAt(i);
			if (   (c == '~') || (c == '`') || (c == '!') || (c == '#') || (c == '$') || (c == '%')
				|| (c == '^') || (c == '&') || (c == '*') || (c == '(') || (c == ')') || (c == '_') || (c == '-')
				|| (c == '+') || (c == '=') || (c == '{') || (c == '[') || (c == ']') || (c == '}') || (c == '|')
				|| (c == '\\') || (c == ':') || (c == ';') || (c == '<') || (c == ',') || (c == '>') 
				|| (c == '?') || (c == '/') || (c == '"') || (c == '\'')
				) {
				mess = " : Không đúng định dạng email, định dạng email : XXXXXXX@XXX.com";
				return true;
			}
		}
		return false;
	}
	
	/* Kiểm tra có dấu space không */
	public boolean isSpace() {
		for (int i = 0; i < InputText.length(); i++) {
			if (InputText.charAt(i) == ' ') {
				mess = " : Có khoảng trắng";
				return true;
			}
		}
		return false;
	}
	
	/* Kiểm tra có phải mã sách không 
	 * Mã sách phải là chữ hoa, nhỏ hơn 9 ký tự
	 */
	public boolean isNotID() {
		if (InputText.length() > 10) {
			mess = " : Mã phải có ít hơn 11 ký tự";
			return true;
		}
		for (int i = 0; i < InputText.length(); i++) {
			char c = InputText.charAt(i);
			if ((c > '9' || c < '0') && (c > 'Z' || c < 'A')) {
				mess = " : Mã chỉ được là chữ hoa và số";
				return true;
			}
		}
		return false;
	}
	
	/* Kiểm tra có phải là năm không */
	public boolean isNotYear() {
		if (InputText.length() == 0) return false;
		if (InputText.length() != 4) {
			mess = " : 1 năm phải có <= 4 số";
			return true;
		}
		return false;
	}
	
	/* show nguyên nhân lỗi */
	public String getMess () {
		return mess;
	}
}
