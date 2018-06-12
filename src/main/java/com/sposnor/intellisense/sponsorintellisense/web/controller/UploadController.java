package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.IOException;
import java.util.List;

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
	
	@Autowired
	S3Wrapper s3Wrapper;

	@GetMapping("/list")
	public List<UploadDocument> listFile(@RequestHeader String type) {
		String fileType = "ST";
		if("sponsor".equalsIgnoreCase(type)) {
			fileType = "SP";
			return uploadMapper.listSponsorUploads(fileType);			
		}
		 System.out.println("  s3Wrapper.list() " + s3Wrapper.list());
		return uploadMapper.listStudentUploads(fileType);
	}
		
	@PostMapping("/upload/{type}/{id}")
	public ResponseEntity<String> uploadImage(
			@RequestParam("userId") String userId,
			@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("initiativeId") String initiativeId,
			@PathVariable(value = "id") Long id,
			@PathVariable(value = "type") String type			
			) throws IOException {
			String message = "";
			String name = null ;
			FileUpload fileUpload = new FileUpload();
			try {
				name = multipartFile.getOriginalFilename();
				fileUpload.setFileName(multipartFile.getOriginalFilename());
				fileUpload.setFileData(multipartFile.getBytes());
				fileUpload.setReferenceId(id);
				fileUpload.setInitiativeId(Long.valueOf(initiativeId));
				if("sponsor".equalsIgnoreCase(type)) {
					fileUpload.setType("SP");
				}else if("student".equalsIgnoreCase(type)) {
					fileUpload.setType("ST");
				}else {
					System.err.println(" Unknown file type "+type);
				}				
				fileUpload.setUserId(Long.valueOf(userId));	
				fileUpload.setUploaduri("https://s3.us-east-2.amazonaws.com/datafileupload/"+multipartFile.getOriginalFilename());
				//uploadMapper.uploadFile(fileUpload);
				PutObjectResult putObjectResult = s3Wrapper.upload(multipartFile);
				//System.out.println( " putObjectResult " +putObjectResult);
				message = "You successfully uploaded " + name + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "FAIL to upload " + name + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
	}
}
