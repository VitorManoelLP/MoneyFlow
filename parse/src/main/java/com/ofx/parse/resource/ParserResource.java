package com.ofx.parse.resource;

import com.client.common.dto.RequestParseDTO;
import com.client.common.dto.TransactionDTO;
import com.ofx.parse.imp.ParseTransactionHandler;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@AllArgsConstructor
public class ParserResource {

	private final List<ParseTransactionHandler> parseTransactionHandlers;

	@RequestMapping(value = "/parse", method = RequestMethod.POST)
	public ResponseEntity<List<TransactionDTO>> parse (@RequestBody @NonNull RequestParseDTO requestParseDTO) throws ExecutionControl.NotImplementedException {

		byte[] fileBytes = Base64.getDecoder().decode(requestParseDTO.getBase64());

		ParseTransactionHandler parserResolved = parseTransactionHandlers.stream()
				.filter(handler -> handler.isSupported(requestParseDTO.getTipoArquivo()))
				.findFirst()
				.orElseThrow(() -> new ExecutionControl.NotImplementedException("Formato não suportado"));

		return ResponseEntity.ok(parserResolved.parse(fileBytes));
	}

}
