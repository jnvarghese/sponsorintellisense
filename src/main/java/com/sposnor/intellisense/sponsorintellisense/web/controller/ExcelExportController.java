package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSummary;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;
import com.sposnor.intellisense.sponsorintellisense.util.ExcelGenerator;

@RestController
@RequestMapping("/api/excel")
public class ExcelExportController {

	Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentMapper studentMapper;
	
	@GetMapping("/students/activeinactive/{id}")
	public ResponseEntity<?> createProject(HttpServletRequest request, HttpServletResponse response,
			@RequestHeader Long userId, @PathVariable(value = "id") Long projectId) {

		List<StudentSummary> summaries = studentMapper.summaryByProjectId(projectId);
		
		List<StudentSummary> activeInActiveStudents = summaries.stream()
				.filter(ss -> ss.getDays().contains("-"))
				.collect(Collectors.toList());
		
		ByteArrayInputStream in;
		try {
			in = ExcelGenerator.customersToExcel(activeInActiveStudents);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<byte[]>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LocalDateTime localDate = LocalDateTime.now();
		String fileName = "CampaignID" + localDate.toString() + ".xlsx";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		headers.add("Content-Disposition", "attachment; filename=" + fileName);
		//String filename = "output.xlsx";
		//headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));

	}

}
