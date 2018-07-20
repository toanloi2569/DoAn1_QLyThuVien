package GetDataFromExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import DataFrame.Main;

/* Hiển thị 1 cửa sổ để chọn file, sử dụng jfilechooser 
 * Kiểm tra xem có đúng là file excel không
 * Sử dụng package jxl để nhận giá trị của lần lượt các dòng
 * Với mỗi dòng, kiểm tra điều kiện để nhận vào 
 * Nếu thỏa mãn điều kiện, thêm vào string values 
 * Nếu không thỏa mãn, hiện 1 thông báo để bào hiệu dòng bị lỗi
 * Sau khi hoàn thành string values, import vào trong database
 * Nếu xảy ra lỗi trong database (trùng khóa chính)
 * Hiện thông báo về dòng trùng, hủy kết quả import
 * */
public abstract class AbstractGetDataFromExcel {
	public JFileChooser window;
	public FileNameExtensionFilter filter;
	public File file;
	public String[] d;
	public int rowIndex;
	
	public AbstractGetDataFromExcel() {
		/* Hiện 1 cửa sổ để chọn file 
		 * Kiểm tra file có phải là excel không*/
		getFileExcel();
		
		/* Xử dụng jxls để nhận giá trị các dòng, kiểm tra đk */
		getDataFromExcel();
	}
	
	private void getFileExcel(){
		window = new JFileChooser();
		filter = new FileNameExtensionFilter("XLSX files","xlsx");
		window.setFileFilter(filter);
		
		/* Chọn file */
		int returnVal = window.showDialog(Main.mainFrame, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			/* Kiểm tra xem file đúng định dạng xlsx không */
			file = window.getSelectedFile();
			if (!file.getName().endsWith("xlsx")) {
				JOptionPane.showMessageDialog(null, "File chọn không đúng định dạng");
				file = null;
				return;
			}
		}
	}
	
	private void getDataFromExcel(){
		try {
			FileInputStream excelFile = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
			XSSFSheet sheet = workbook.getSheetAt(0); 
			int cellNum = sheet.getRow(0).getLastCellNum();
			int rowNum = sheet.getLastRowNum()+1;
			d = new String[cellNum];
			
			Iterator<Row> rowIterator = sheet.iterator();
			Row row = rowIterator.next();
			
			/* Duyệt lần lượt từng dòng của sheet */
			rowIndex = 2;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
					Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					if (cell.getCellTypeEnum() == CellType.STRING) {
						d[cellIndex] = cell.getStringCellValue()+"";
					}
					else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						d[cellIndex] = (int)cell.getNumericCellValue()+"";
					}
					else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
						d[cellIndex] = cell.getBooleanCellValue()+"";
					}
					else {
						d[cellIndex] = null;
					}
				}
				/* Nếu dòng thỏa mãn thì import vào database */
				if (isError()) {
					updateTable();
					JOptionPane.showMessageDialog(null, "Dữ liệu nhập sai tại dòng : "+ rowIndex);
					return;
				} else {
					if (importData()) {
						rowIndex++;
						if (rowIndex < rowNum) break;
					}
					else return;
				}
			}
			updateTable();
			JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công");
		
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "File không có dữ liệu, nhập lại file");
			return;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File không tồn tại");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	abstract boolean importData();

	abstract boolean isError();
	
	abstract void updateTable();
}
