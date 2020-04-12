package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.sposnor.intellisense.sponsorintellisense.data.model.FileUpload;
import com.sposnor.intellisense.sponsorintellisense.data.model.UploadDocument;
import com.sposnor.intellisense.sponsorintellisense.mapper.UploadMapper;
import com.sposnor.intellisense.sponsorintellisense.s3.S3Wrapper;

@RestController
@RequestMapping("/api/file")
public class UploadController {

	@Autowired
	private UploadMapper uploadMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	private int numberOfColumns = -1;

	private final String[] STUDENT_HEADER_EXPECTED = { "CODE", "NAME OF CHILD", "GENDER", "DATE OF BIRTH", "GRADE",
			"HOBBY", "TALENT", "FAVORITE GAME", "FAVORITE COLOR", "NAME OF PARENT", "OCCUPATION OF PARENT",
			"PROJECT LOCATION", "MOTHER TONGUE", "ADD LINK FOR PICTURE" };

	@Autowired
	S3Wrapper s3Wrapper;

	@GetMapping("/list")
	public List<UploadDocument> listFile(@RequestHeader String type) {
		String fileType = "ST";
		if ("sponsor".equalsIgnoreCase(type)) {
			fileType = "SP";
			return uploadMapper.listSponsorUploads(fileType);
		}
		// System.out.println(" s3Wrapper.list() " + s3Wrapper.list());
		return uploadMapper.listStudentUploads(fileType);
	}

	@PostMapping("/upload/{type}/{id}")
	public ResponseEntity<String> uploadImage(@RequestParam("userId") String userId,
			@RequestParam("file") MultipartFile multipartFile, @RequestParam("initiativeId") String initiativeId,
			@PathVariable(value = "id") Long id, @PathVariable(value = "type") String type) throws IOException {
		String message = "";
		String name = null;
		FileUpload fileUpload = new FileUpload();
		try {

			XSSFWorkbook wb = new XSSFWorkbook(multipartFile.getInputStream());
			XSSFSheet sheet = wb.getSheetAt(0);

			name = multipartFile.getOriginalFilename();
			fileUpload.setFileName(multipartFile.getOriginalFilename());
			// fileUpload.setFileData(multipartFile.getBytes());
			fileUpload.setReferenceId(id);
			fileUpload.setInitiativeId(Long.valueOf(initiativeId));
			if ("sponsor".equalsIgnoreCase(type)) {
				fileUpload.setType("SP");
			} else if ("student".equalsIgnoreCase(type)) {
				String[] actual = getRow(0, sheet);
				if (!Arrays.equals(STUDENT_HEADER_EXPECTED, actual)) {
					message = "Column header does not match as expected !. Expected is "
							+ Arrays.toString(STUDENT_HEADER_EXPECTED) + ". Actual is " + Arrays.toString(actual);
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
				}else if(getErroredDates(sheet).size()>0) {
					Map<Integer,String> erroredDates = getErroredDates(sheet);
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Invalid dates in rows; " +erroredDates.toString());
				}
				fileUpload.setType("ST");
			} else {
				System.err.println(" Unknown file type " + type);
			}
			fileUpload.setUserId(Long.valueOf(userId));
			fileUpload.setUploaduri(
					"https://s3.us-east-2.amazonaws.com/datafileupload/" + multipartFile.getOriginalFilename());
			uploadMapper.uploadFile(fileUpload);
			try {
				PutObjectResult putObjectResult = s3Wrapper.upload(multipartFile);
			} catch (SdkClientException ex) {
				ex.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SdkClientException "+ex.getMessage());
			}
			// LOGGER.debug( "Put Object Result {} ", putObjectResult);
			message = "You successfully uploaded " + name + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "FAIL to upload " + name + "!";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}

	private Map<Integer, String> getErroredDates(org.apache.poi.ss.usermodel.Sheet sheet) {
		final Map<Integer, String> erroredDates = new HashMap<Integer, String>();
		for (Row row : sheet) { // For each Row.
			if (row.getRowNum() == 0) {
				continue; // just skip the rows if row number is 0 or 1
			}
			Cell cell = row.getCell(3); // Get the Cell at the Index / Column you want.
			switch (cell.getCellType()) {
			case NUMERIC: {
				// cells.add(String.valueOf(cell.getNumericCellValue()));
				if (DateUtil.isCellDateFormatted(cell)) {
					//System.out.println("Row No.: " + row.getRowNum() + " " + cell.getDateCellValue());
					try {
						// System.out.println(" --> "+ cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						// cells.add(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString());
					} catch ( Exception e) {
						erroredDates.put(row.getRowNum() + 1, e.getMessage());
						e.printStackTrace();
					}
				} else {
					erroredDates.put(row.getRowNum() + 1, "Non DateUtil Case");
				}
				break;
			}
			case STRING:
				erroredDates.put(row.getRowNum() + 1, String.valueOf(cell.getStringCellValue()));
				break;
			default:
				erroredDates.put(row.getRowNum() + 1, "Unsupported Cell type");
			}
		}
		return erroredDates;
	}

	private String[] getRow(final int rowNumber, org.apache.poi.ss.usermodel.Sheet sheet) {

		final Row row = sheet.getRow(rowNumber);
		if (row == null) {
			return null;
		}
		final List<String> cells = new LinkedList<String>();

		for (int i = 0; i < getNumberOfColumns(sheet); i++) {
			Cell cell = row.getCell(i);
			switch (cell.getCellType()) {
			case NUMERIC:
				cells.add(String.valueOf(cell.getNumericCellValue()).trim());
				break;
			case STRING:
				cells.add(String.valueOf(cell.getStringCellValue()).trim());
				break;
			case BLANK:
				cells.add(cell.getStringCellValue().trim());
				break;
			default:
				throw new IllegalArgumentException("Cannot handle cells of type " + cell.getCellType());
			}
		}
		return cells.toArray(new String[cells.size()]);
	}

	private int getNumberOfColumns(org.apache.poi.ss.usermodel.Sheet sheet) {
		if (numberOfColumns < 0) {
			numberOfColumns = sheet.getRow(0).getLastCellNum();
		}
		return numberOfColumns;
	}
}
