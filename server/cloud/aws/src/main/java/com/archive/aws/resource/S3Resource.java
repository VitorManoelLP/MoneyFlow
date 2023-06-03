package com.archive.aws.resource;

import com.archive.aws.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Resource {

	private final S3Service s3Service;

	@GetMapping
	public ListObjectsResponse getBuckets(@RequestParam("bucketName") String bucketName) {
		return s3Service.listObjects(bucketName);
	}

}
