package cn.zj.cloud.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import cn.zj.cloud.model.StockPriceRowModel;

public class ExcelHandler {

	public static List<StockPriceRowModel> getStockPriceListFromExcel(InputStream inputStream) throws Exception {
		List<StockPriceRowModel> list = new ArrayList<>();

		// create workbook
		Workbook workbook = WorkbookFactory.create(inputStream);
		inputStream.close();
		// get work sheet
		Sheet sheet = workbook.getSheetAt(0);
		// total row number Index
		int totalRowNumIdx = sheet.getLastRowNum()+1;
		// row header
		Row header = sheet.getRow(0);
		
		// column number Index
		int totalColumnNumIdx = header.getLastCellNum();
		for(int currentRowNum=1; currentRowNum < totalRowNumIdx; currentRowNum ++) {
			Row currentRow = sheet.getRow(currentRowNum);
			StockPriceRowModel model = new StockPriceRowModel();
			for(int currentColNum=0; currentColNum< totalColumnNumIdx; currentColNum++) {
				Cell cell = currentRow.getCell(currentColNum);
				System.out.println(cell);
				if(!StringUtil.isNull(cell)) {
					switch(currentColNum) {
						// set Company Code to StockPriceRowModel
						case 0:model.setCompanycode(cell.getStringCellValue());break;
						// set Stock Exchange to StockPriceRowModel
						case 1:model.setStockcode(cell.getStringCellValue());break;
						// set Stock Code to StockPriceRowModel
						case 2:model.setStockexchange(cell.getStringCellValue());break;
						// set Price per share to StockPriceRowModel
						case 3:model.setCurrentprice(Double.valueOf(cell.getStringCellValue()));break;
						// set currency to StockPriceRowModel
						case 4:model.setCurrency(cell.getStringCellValue());break;
						// set Date StockPriceRowModel
						case 5:model.setDate(cell.getStringCellValue());break;
						// set Time StockPriceRowModel
						case 6:model.setTime(cell.getStringCellValue());break;
					}
				}
			}
			list.add(model);
		}

		return list;
	}
}
