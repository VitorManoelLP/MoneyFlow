package com.ofx.parse.service;

import com.client.common.dto.TransactionDTO;
import com.client.common.enums.TipoArquivo;
import com.ofx.parse.client.MoneyFlowClient;
import com.ofx.parse.imp.ParseTransactionHandler;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParserService {

	private final List<ParseTransactionHandler> parseTransactionHandlers;

	private final MoneyFlowClient moneyFlowClient;

	public void parse(MultipartFile multipartFile) throws ExecutionControl.NotImplementedException, IOException {

		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

		ParseTransactionHandler parserResolved = parseTransactionHandlers.stream()
				.filter(handler -> handler.isSupported(TipoArquivo.getByIdentificador(extension)))
				.findFirst()
				.orElseThrow(() -> new ExecutionControl.NotImplementedException("Formato não suportado"));

		List<TransactionDTO> parse = parserResolved.parse(multipartFile.getBytes());

		moneyFlowClient.salvarExtrato(parse);
	}

}
