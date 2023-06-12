package com.archive.aws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Client s3Client;
	public void putObject(String bucketName, String key, String value) {
		s3Client.putObject(b -> b.bucket(bucketName).key(key), Paths.get(value));
	}

	public PutObjectResponse putFile(String bucketName, String key, File file) {
		return s3Client.putObject(b -> b.bucket(bucketName).key(key), RequestBody.fromFile(file));
	}

	public ListObjectsResponse listObjects(String bucketName) {
		return s3Client.listObjects(b -> b.bucket(bucketName));
	}

}
