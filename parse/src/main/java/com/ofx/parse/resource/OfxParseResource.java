package com.ofx.parse.resource;

import com.client.common.dto.TransactionDTO;
import com.ofx.parse.service.OfxParseTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class OfxParseResource {

	public final OfxParseTransactionService ofxParseTransaction;

	@RequestMapping(value = "/parse-ofx", method = RequestMethod.POST)
	public ResponseEntity<TransactionDTO> parseOfx (@RequestBody MultipartFile file) {
		return ResponseEntity.ok(ofxParseTransaction.parseOfx(file));
	}

}
