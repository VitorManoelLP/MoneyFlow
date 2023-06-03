package com.ofx.parse.resource;

import com.client.common.dto.RequestParseDTO;
import com.client.common.enums.TipoArquivo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ofx.parse.service.CSVParseTransactionService;
import com.ofx.parse.service.OfxParseTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OfxParseResourceTest {

	@Spy
	private OfxParseTransactionService ofxParseTransactionService;

	@Spy
	private CSVParseTransactionService csvParseTransactionService;

	private MockMvc mockMvc;

	private final ObjectWriter objectWriter = new ObjectMapper().writer();

	private FileInputStream fileInputStream;

	@Before
	public void setup() {

		doReturn(List.of()).when(ofxParseTransactionService).parse(any());
		doReturn(List.of()).when(csvParseTransactionService).parse(any());

		ParserResource ofxParseResource = new ParserResource(List.of(ofxParseTransactionService, csvParseTransactionService));
		mockMvc = MockMvcBuilders.standaloneSetup(ofxParseResource).build();
	}

	@AfterEach
	public void after() throws IOException {
		fileInputStream.close();
	}

	@Test
	public void parseOfx() throws Exception {

		fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:OFX_EXAMPLE.ofx"));

		byte[] bytes = fileInputStream.readAllBytes();

		String base64File = Base64.getEncoder().encodeToString(bytes);

		RequestParseDTO requestParseDTO = new RequestParseDTO(base64File, TipoArquivo.OFX);

		String payload = objectWriter.writeValueAsString(requestParseDTO);

		mockMvc.perform(post("/parse")
						.content(payload)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(ofxParseTransactionService).parse(bytes);
	}

	@Test
	public void parseCsv() throws Exception {

		fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:CSV_EXAMPLE.csv"));

		byte[] bytes = fileInputStream.readAllBytes();

		String base64File = Base64.getEncoder().encodeToString(bytes);

		RequestParseDTO requestParseDTO = new RequestParseDTO(base64File, TipoArquivo.CSV);

		String payload = objectWriter.writeValueAsString(requestParseDTO);

		mockMvc.perform(post("/parse")
						.content(payload)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(csvParseTransactionService).parse(bytes);
	}

}
