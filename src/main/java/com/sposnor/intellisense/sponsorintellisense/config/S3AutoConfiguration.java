package com.sposnor.intellisense.sponsorintellisense.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3AutoConfiguration {

	@Value("${amazon.s3.region}")
	private String region;

	@Bean
	public com.amazonaws.auth.AWSCredentials basicAWSCredentials() {
		return null;
	}

	@Bean
	public AWSCredentialsProvider awsCredentialsProvider() {
		return new InstanceProfileCredentialsProvider(false);
	}

	@Bean
	public AmazonS3 amazonS3Client(AWSCredentials awsCredentials) {
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard();
		builder.withCredentials(awsCredentialsProvider());
		builder.setRegion(region);
		AmazonS3 amazonS3 = builder.build();
		return amazonS3;

	}
}