package com.ofx.parse.service;

import com.ofx.parse.client.MoneyFlowClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParserServiceTest {

	@Spy
	private OfxParseTransactionService ofxParseTransactionService;

	@Spy
	private CSVParseTransactionService csvParseTransactionService;

	private ParserService parserService;

	private FileInputStream fileInputStream;

	private final MoneyFlowClient moneyFlowClient = mock(MoneyFlowClient.class);

	@Before
	public void setup() {

		doReturn(List.of()).when(ofxParseTransactionService).parse(any());
		doReturn(List.of()).when(csvParseTransactionService).parse(any());

		parserService = new ParserService(List.of(ofxParseTransactionService, csvParseTransactionService), moneyFlowClient);
	}

	@AfterEach
	public void after() throws IOException {
		fileInputStream.close();
	}

	@Test
	public void parseOfx() throws Exception {

		fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:OFX_EXAMPLE.ofx"));

		MockMultipartFile file = new MockMultipartFile("file", "file", "application/x-ofx",fileInputStream);

		parserService.parse(file);

		verify(ofxParseTransactionService).parse(file.getBytes());
		verify(moneyFlowClient).salvarExtrato(any());
	}

	@Test
	public void parseCsv() throws Exception {

		fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:CSV_EXAMPLE.csv"));

		MockMultipartFile file = new MockMultipartFile("file", "file", "text/csv", fileInputStream);

		parserService.parse(file);

		verify(csvParseTransactionService).parse(file.getBytes());
		verify(moneyFlowClient).salvarExtrato(any());
	}

}
