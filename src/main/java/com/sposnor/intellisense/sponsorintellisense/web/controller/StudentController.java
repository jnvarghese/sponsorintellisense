package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
    SimpleDateFormat MYSQL_DT_FORMAT = new SimpleDateFormat("yyyyy-MM-dd"); 

	@Autowired
	private StudentMapper studentMapper;
	
	@GetMapping("/list")
	public List<Student> getAllStudents() {
		return studentMapper.list();
	}
	
	@GetMapping("/list/byproject/{id}")
	public List<Student> getAllStudentsByProjectId(@PathVariable(value = "id") Long projectId) {
		return studentMapper.listByProjectId(projectId);
	}
	
	@PostMapping("/add")
	public Student createStudent(@RequestHeader Long userId, @RequestBody Student note) {
		note.setCreatedBy(userId);
		studentMapper.insert(note);	    
	    return note;
	}	
	@PostMapping("/image/{id}")
	public ResponseEntity<String> uploadImage(
			@RequestParam("file") MultipartFile multipartFile,
			@PathVariable(value = "id") Long studentId) throws IOException {
			String message = "";
			String name = null ;
			Student student = new Student();
			ByteArrayOutputStream baos  = new ByteArrayOutputStream();
			try {
				name = multipartFile.getOriginalFilename();
				
				System.out.println("File name: "+multipartFile);				
				convertFormat(multipartFile.getInputStream(), "png", baos);
				System.out.println( " - imageBytes -" + baos.toByteArray());
				byte[] arr = baos.toByteArray();
				//student.setProfilePicture(multipartFile.getBytes());
				student.setProfilePicture(arr);
				student.setId(studentId);
				studentMapper.uploadImage(student);
				message = "You successfully uploaded " + name + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "FAIL to upload " + name + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
			}finally {
				baos.close();
			}
	}
	
	private OutputStream convertFormat(InputStream inputStream, String formatName, OutputStream baos) throws IOException {
		
			//ByteArrayOutputStream baos  = new ByteArrayOutputStream();			
			byte [] currentImage;
			// reads input image from file
			BufferedImage inputImage = ImageIO.read(inputStream);

			// writes to the output image in specified format
			boolean result = ImageIO.write(inputImage, "png", baos);
			System.out.println( " - result -- "+result);
			//baos.flush();
			
		    //currentImage = baos.toByteArray();
		    //baos.close();
			//inputStream.close();

			return baos;
			}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long studentId) {
		Student note = studentMapper.findById(studentId);
	    if(note == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(note);
	}
	
	@PutMapping("/modify/{id}")
	public Student updateStudent(@RequestHeader Long userId,@PathVariable(value = "id") Long studentId, 
			@Valid @RequestBody Student studentToModify) {	
		studentToModify.setUpdatedBy(userId);
		studentMapper.update(studentToModify);	    
	    return studentToModify;
	}
	
	@GetMapping("/search/{name}/{projectId}/{month}/{date}/{year}")
	public ResponseEntity<List<Student>> getStudentByName(
			@PathVariable(value = "name") String name,
			@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "month") int month,
			@PathVariable(value = "date") int date,
			@PathVariable(value = "year") int year
			) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1, 0, 0);  
		System.out.println(" Effective Date in search "+ c.getTime());
		List<Student> sponsors = studentMapper.findStudentsBySponsorshipStatus(name+"%", projectId, MYSQL_DT_FORMAT.format(c.getTime()));
	    if(sponsors == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsors);
	}
	
	@GetMapping("/search/{name}")
	public List<Student> getStudentsByName(
			@PathVariable(value = "name") String name){
		return studentMapper.listMatchingStudentsByName(name+"%");
	}
	
	
}
