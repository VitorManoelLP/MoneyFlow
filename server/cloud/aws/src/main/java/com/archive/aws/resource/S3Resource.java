package com.archive.aws.resource;

import com.archive.aws.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Resource {

	private final S3Service s3Service;

	@GetMapping
	public ListObjectsResponse getBuckets(@RequestParam("bucketName") String bucketName) {
		return s3Service.listObjects(bucketName);
	}

	@PostMapping
	public ResponseEntity<String> salvar(@RequestParam("bucketName") String bucketName,
	                                     @RequestParam("key") String key,
	                                     @RequestBody File value) {

		PutObjectResponse putObjectResponse = s3Service.putFile(bucketName, key, value);
		return ResponseEntity.ok("Arquivo salvo com sucesso!");
	}


}
