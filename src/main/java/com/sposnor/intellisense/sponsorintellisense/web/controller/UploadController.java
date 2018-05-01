package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sposnor.intellisense.sponsorintellisense.data.model.FileUpload;
import com.sposnor.intellisense.sponsorintellisense.mapper.UploadMapper;

@RestController
@RequestMapping("/api")
public class UploadController {
	
	@Autowired
	private UploadMapper uploadMapper;

	@PostMapping("/upload/{agencyId}/{projectId}/{userId}")
	public ResponseEntity<String> uploadImage(
			@RequestParam("file") MultipartFile multipartFile,
			@PathVariable(value = "agencyId") Long agencyId, 
			@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "userId") Long userId
			) throws IOException {
			String message = "";
			String name = null ;
			FileUpload fileUpload = new FileUpload();
			try {
				name = multipartFile.getOriginalFilename();
				System.out.println("File name: "+multipartFile);
				fileUpload.setFileName(multipartFile.getOriginalFilename());
				fileUpload.setFileData(multipartFile.getBytes());
				fileUpload.setProjectId(projectId);
				fileUpload.setAgencyId(agencyId);
				fileUpload.setUserId(userId);
				uploadMapper.uploadFile(fileUpload);
				message = "You successfully uploaded " + name + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "FAIL to upload " + name + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
	}
}
