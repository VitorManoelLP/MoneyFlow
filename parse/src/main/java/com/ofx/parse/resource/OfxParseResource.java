package com.ofx.parse.resource;

import com.client.common.dto.TransactionDTO;
import com.ofx.parse.service.OfxParseTransactionService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@AllArgsConstructor
public class OfxParseResource {

	public final OfxParseTransactionService ofxParseTransaction;

	@RequestMapping(value = "/parse-ofx", method = RequestMethod.POST)
	public ResponseEntity<List<TransactionDTO>> parseOfx (@RequestBody @NonNull String base64File) throws IOException {

		byte[] fileBytes = Base64.getDecoder().decode(base64File);

		return ResponseEntity.ok(ofxParseTransaction.parseOfx(fileBytes));
	}

}
