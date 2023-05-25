package com.ofx.parse.service;

import com.client.common.dto.TransactionDTO;
import com.client.common.enums.TipoRendimento;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OfxParseTransactionServiceTest {

	private OfxParseTransactionService ofxParseTransactionService;

	private FileInputStream fileInputStream;

	@Before
	public void setup() throws FileNotFoundException {
		fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:OFX_EXAMPLE.ofx"));
		ofxParseTransactionService = new OfxParseTransactionService();
	}

	@After
	public void afterAll() throws IOException {
		fileInputStream.close();
	}

	@Test
	public void parseOfx() throws IOException {

		MockMultipartFile mockMultipartFile = new MockMultipartFile("nu.ofx", "NU23131.ofx", "multipart/form-data", fileInputStream.readAllBytes());

		List<TransactionDTO> transactions = ofxParseTransactionService.parseOfx(mockMultipartFile);

		Assertions.assertThat(transactions)
				.hasSize(1)
				.isNotEmpty()
				.extracting(TransactionDTO::getCompetencia)
				.allSatisfy(competencia -> Assertions.assertThat(competencia).isEqualTo(5L));

		Assertions.assertThat(transactions)
				.extracting(TransactionDTO::getDescricao)
				.containsOnly("DESPESA NUBANK 31/05/2023");

		Assertions.assertThat(transactions).extracting(TransactionDTO::getTipoRendimento)
				.containsOnly(TipoRendimento.DESPESA);

		Assertions.assertThat(transactions).flatExtracting(TransactionDTO::getDetails)
				.allSatisfy(detail -> {
					Assertions.assertThat(detail.getValor()).isCloseTo(new BigDecimal("50.00"), Offset.offset(new BigDecimal("0.01")));
					Assertions.assertThat(detail.getDescricao()).isEqualTo("Pagamento 1");
					Assertions.assertThat(detail.getData()).isEqualTo(LocalDate.of(2023, 5, 1));
				});

	}


}
