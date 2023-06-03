package com.ofx.parse.resource;

import com.ofx.parse.service.ParserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OfxParseResourceTest {

	@Mock
	private ParserService parserService;

	private MockMvc mockMvc;

	private FileInputStream fileInputStream;

	@Before
	public void setup() {
		ParserResource ofxParseResource = new ParserResource(parserService);
		mockMvc = MockMvcBuilders.standaloneSetup(ofxParseResource).build();
	}

	@AfterEach
	public void after() throws IOException {
		fileInputStream.close();
	}

	@Test
	public void parse() throws Exception {

		fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:OFX_EXAMPLE.ofx"));

		MockMultipartFile file = new MockMultipartFile("file", fileInputStream);

		mockMvc.perform(multipart("/parse")
						.file(file)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(parserService).parse(file);
	}

}
