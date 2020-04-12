package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainRunner {

	private final org.apache.poi.ss.usermodel.Sheet delegate;

	private final int numberOfRows;
	private final String name;

	private int numberOfColumns = -1;
	// private FormulaEvaluator evaluator;

	MainRunner(final org.apache.poi.ss.usermodel.Sheet delegate) {
		super();
		this.delegate = delegate;
		this.numberOfRows = this.delegate.getLastRowNum() + 1;
		this.name = this.delegate.getSheetName();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		final Map<Integer,String> erroredDates = new HashMap<Integer, String>();
		DateFormat df = new SimpleDateFormat("MM/dd/YYYY");
		FileInputStream file = new FileInputStream(new File("C://Users//HP//Downloads//KSHIPRA1.xlsx"));
		// creating Workbook instance that refers to .xlsx file
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0); // creating a Sheet object to retrieve object

		// Iterator<Row> itr = sheet.iterator(); // iterating over excel file
		for (Row row : sheet) { // For each Row.
			if (row.getRowNum() == 0) {
				continue; // just skip the rows if row number is 0 or 1
			}
			Cell cell = row.getCell(3); // Get the Cell at the Index / Column you want.
			switch (cell.getCellType()) {
			case NUMERIC:{
				//cells.add(String.valueOf(cell.getNumericCellValue()));
				if (DateUtil.isCellDateFormatted(cell)) {
			        System.out.println ("Row No.: " + row.getRowNum ()+ " " + 
			            cell.getDateCellValue());
			        try {
			        	System.out.println(" --> "+cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			        	//cells.add(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
			        } catch (final NullPointerException e) {
			        	e.printStackTrace();
			        }
			    }else {
			    	 System.out.println ("Row No.: " + row.getRowNum ()+ " Date issue ");
			    }
				break;
			}	
			case STRING:
				erroredDates.put(row.getRowNum()+1 ,String.valueOf(cell.getStringCellValue()));
				break;
			default:
				erroredDates.put(row.getRowNum()+1 ,"Unsupported Cell type");
			}
		}
		System.out.println(" 0 " + erroredDates);

	}

	public String[] getRow(final int rowNumber) {

		final Row row = this.delegate.getRow(rowNumber);
		if (row == null) {
			return null;
		}
		final List<String> cells = new LinkedList<String>();

		// for (int i = 0; i < getNumberOfColumns(); i++) {
		Cell cell = row.getCell(3);
		switch (cell.getCellType()) {
		case NUMERIC:
			cells.add(String.valueOf(cell.getNumericCellValue()));

			break;
		case STRING:
			cells.add(String.valueOf(cell.getBooleanCellValue()));
			break;

		case BLANK:
			cells.add(cell.getStringCellValue());
			break;

		default:
			throw new IllegalArgumentException("Cannot handle cells of type " + cell.getCellType());
		}
		// }
		return cells.toArray(new String[cells.size()]);
	}

	public int getNumberOfColumns() {
		if (numberOfColumns < 0) {
			numberOfColumns = this.delegate.getRow(0).getLastCellNum();
		}
		return numberOfColumns;
	}
}
