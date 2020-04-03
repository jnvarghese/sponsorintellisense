package com.sposnor.intellisense.sponsorintellisense.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSummary;

public class ExcelGenerator {

	public static ByteArrayInputStream customersToExcel(List<StudentSummary> summary) throws IOException {
	    String[] COLUMNs = {"Student Name", "Student Code", "Gender", "Sponsor Name", "Sponsor Code", "Sponsor Parish", "Sponsorship Expired", "Days Past"};
	    try(
	        Workbook workbook = new XSSFWorkbook();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ){
	      CreationHelper createHelper = workbook.getCreationHelper();
	   
	      Sheet sheet = workbook.createSheet("Active In Active");
	   
	      Font headerFont = workbook.createFont();
	      headerFont.setBold(true);
	      headerFont.setColor(IndexedColors.BLUE.getIndex());
	   
	      CellStyle headerCellStyle = workbook.createCellStyle();
	      headerCellStyle.setFont(headerFont);
	   
	      // Row for Header
	      Row headerRow = sheet.createRow(0);
	   
	      // Header
	      for (int col = 0; col < COLUMNs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(COLUMNs[col]);
	        cell.setCellStyle(headerCellStyle);
	      }
	   
	      // CellStyle for Age
	      CellStyle ageCellStyle = workbook.createCellStyle();
	      ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
	   
	      int rowIdx = 1;
	     // String[] COLUMNs = {"Student Name", "Student Code", "Gender", "Sponsor Name", "Sponsor Code", "Sponsor Parish", "Sponsorship Expired", "Days Past"};
		    
	      for (StudentSummary student : summary) {
	        Row row = sheet.createRow(rowIdx++);
	   
	        row.createCell(0).setCellValue(student.getStudentName());
	        row.createCell(1).setCellValue(student.getStudentCode());
	        row.createCell(2).setCellValue(student.getGender());
	        row.createCell(3).setCellValue(student.getSponsorfirstName()+" "+student.getSponsorMiddleInitial()+" "+student.getSponsorLastName());
	        row.createCell(4).setCellValue(student.getSponsorCode());
	        row.createCell(5).setCellValue(student.getParishName()+" "+student.getParishCity());
	        row.createCell(6).setCellValue(student.getMaxout());
	        row.createCell(7).setCellValue(student.getDays());
	   
	       // Cell ageCell = row.createCell(3);
	       // ageCell.setCellValue(customer.getAge());
	       // ageCell.setCellStyle(ageCellStyle);
	      }
	   
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    }
	  }
}
