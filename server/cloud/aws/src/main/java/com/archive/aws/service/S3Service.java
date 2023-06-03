package com.archive.aws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Client s3Client;

	public void createBucket(String bucketName) {
		s3Client.createBucket(b -> b.bucket(bucketName));
	}

	public void deleteBucket(String bucketName) {
		s3Client.deleteBucket(b -> b.bucket(bucketName));
	}

	public void putObject(String bucketName, String key, String value) {
		s3Client.putObject(b -> b.bucket(bucketName).key(key), Paths.get(value));
	}

	public void deleteObject(String bucketName, String key) {
		s3Client.deleteObject(b -> b.bucket(bucketName).key(key));
	}

	public ResponseInputStream<GetObjectResponse> getObject(String bucketName, String key) {
		return s3Client.getObject(b -> b.bucket(bucketName).key(key));
	}

	public ListObjectsResponse listObjects(String bucketName) {
		return s3Client.listObjects(b -> b.bucket(bucketName));
	}

}
