package com.ofx.parse.resource;

import com.client.common.dto.RequestParseDTO;
import com.client.common.dto.TransactionDTO;
import com.client.common.enums.TipoArquivo;
import com.ofx.parse.imp.ParseTransactionHandler;
import com.ofx.parse.service.ParserService;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@AllArgsConstructor
public class ParserResource {

	private final ParserService parserService;

	@RequestMapping(value = "/api/parse", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> parse (@RequestParam("file") @NonNull MultipartFile multipartFile) throws ExecutionControl.NotImplementedException, IOException {
		parserService.parse(multipartFile);
		return ResponseEntity.ok().build();
	}

}
