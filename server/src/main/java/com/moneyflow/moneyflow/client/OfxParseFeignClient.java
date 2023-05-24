package com.moneyflow.moneyflow.client;

import com.client.common.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "ofx-parse", url = "${ofx-parse-url:http://localhost:8081}")
public interface OfxParseFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "parse-ofx")
	ResponseEntity<TransactionDTO> parseOfx(MultipartFile file);

}
