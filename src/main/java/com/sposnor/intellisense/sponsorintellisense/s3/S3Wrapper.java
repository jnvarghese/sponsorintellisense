package com.sposnor.intellisense.sponsorintellisense.s3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class S3Wrapper {

	@Autowired
	private AmazonS3 amazonS3Client;

	@Value("${amazon.s3.student-profile-picture}")
	private String student_profile_picture_bucket;
	
	@Value("${amazon.s3.default-bucket}")
	private String bucket;
	
	private static String SUFFIX = "/";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3Wrapper.class);

	public void createFolder(String folderName) {
		LOGGER.debug("Creating Folder " +folderName);
	    // create meta-data for your folder and set content-length to 0
	    ObjectMetadata metadata = new ObjectMetadata();
	    metadata.setContentLength(0);

	    // create empty content
	    InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

	    // create a PutObjectRequest passing the folder name suffixed by /
	    PutObjectRequest putObjectRequest = new PutObjectRequest(student_profile_picture_bucket,
	                folderName + SUFFIX, emptyContent, metadata);

	    // send request to S3 to create folder
	    amazonS3Client.putObject(putObjectRequest);
	}
	public byte[] downloadProfilePicture(String project, String key, String subkey) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		if(!StringUtils.isEmpty(subkey)) {
			sb.append("-");
			sb.append(subkey);
		}
		sb.append(".png");
		LOGGER.debug("Download profile picture for project,  " +project+ " and file name "+sb.toString());
		
		GetObjectRequest getObjectRequest = new GetObjectRequest(student_profile_picture_bucket+"/"+project, sb.toString());

		S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

		S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

		byte[] bytes = IOUtils.toByteArray(objectInputStream);

		return bytes;
	}
	
	private PutObjectResult upload(InputStream inputStream, String uploadKey) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());

		putObjectRequest.setCannedAcl(CannedAccessControlList.Private);

		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

		IOUtils.closeQuietly(inputStream);

		return putObjectResult;
	}
	
	public PutObjectResult upload(InputStream inputStream, Long uploadKey, String imageLinkRef, Long project, String uploadedBy) {
		
		String bucket = student_profile_picture_bucket+"/"+project;
		
		StringBuilder sb = new StringBuilder();
		sb.append(uploadKey);
		if(!StringUtils.isEmpty(imageLinkRef)) {
			sb.append("-");
			sb.append(imageLinkRef);
		}
		sb.append(".png");
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/png");
        metadata.addUserMetadata("x-amz-meta-title", "uploaded by "+uploadedBy);
        
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, sb.toString(), inputStream, metadata);

		putObjectRequest.setCannedAcl(CannedAccessControlList.Private);

		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

		IOUtils.closeQuietly(inputStream);

		return putObjectResult;
	}

	public PutObjectResult upload(MultipartFile multipartFile) {
		PutObjectResult putObjectResult = null;
		try {
			putObjectResult = upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return putObjectResult;
	}
	
	public List<PutObjectResult> upload(MultipartFile[] multipartFiles) {
		List<PutObjectResult> putObjectResults = new ArrayList<>();

		Arrays.stream(multipartFiles).filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
				.forEach(multipartFile -> {
					try {
						putObjectResults
								.add(upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

		return putObjectResults;
	}

	public ResponseEntity<byte[]> download(String key) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);

		S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

		S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

		byte[] bytes = IOUtils.toByteArray(objectInputStream);

		String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(bytes.length);
		httpHeaders.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

	

	
	public List<S3ObjectSummary> list() {
		ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));

		List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

		return s3ObjectSummaries;
	}
	/*
	private File createImageFileFromByte(Student st, int prjId) throws IOException {
		

		 ByteArrayInputStream bis = new ByteArrayInputStream(st.getProfilePicture());
	     Iterator<?> readers = ImageIO.getImageReadersByFormatName("png");

	     ImageReader reader = (ImageReader) readers.next();
	     Object source = bis; 
	     ImageInputStream iis = ImageIO.createImageInputStream(source); 
	     reader.setInput(iis, true);
	     ImageReadParam param = reader.getDefaultReadParam();

	     Image image = reader.read(0, param);
	     //got an image file

	     BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
	     //bufferedImage is the RenderedImage to be written

	     Graphics2D g2 = bufferedImage.createGraphics();
	     g2.drawImage(image, null, null);

	
	     File imageFile = new File(path);
	     ImageIO.write(bufferedImage, "png", imageFile);

	}*/
}