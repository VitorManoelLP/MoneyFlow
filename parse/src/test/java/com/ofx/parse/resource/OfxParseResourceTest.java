package com.ofx.parse.resource;

import com.ofx.parse.service.OfxParseTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OfxParseResourceTest {

	@InjectMocks
	private OfxParseResource ofxParseResource;

	private final OfxParseTransactionService ofxParseTransactionService = mock(OfxParseTransactionService.class);

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(ofxParseResource).build();
	}

	@Test
	public void parseOfx() throws Exception {

		MockMultipartFile file = new MockMultipartFile("file", "file", "multipart/form-data", "file".getBytes());

		mockMvc.perform(multipart("/parse-ofx")
						.file(file)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk());

		verify(ofxParseTransactionService).parseOfx(file);
	}

}
