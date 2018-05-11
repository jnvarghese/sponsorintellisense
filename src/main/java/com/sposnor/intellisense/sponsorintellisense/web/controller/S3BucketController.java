package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.sposnor.intellisense.sponsorintellisense.s3.S3Wrapper;

@RestController
public class S3BucketController {
	
	@Autowired
	private S3Wrapper s3wrapper;
	
	@GetMapping("/s3/bucketlist")
	public List<S3ObjectSummary> getProjects() {
		return s3wrapper.list();
	}

}
