package com.ofx.parse.service;

import com.client.common.dto.TransactionDTO;
import com.client.common.enums.TipoRendimento;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
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
public class CSVParseTransactionServiceTest {

	private CSVParseTransactionService csvParseTransactionService;
	private FileInputStream fileInputStream;

	@Before
	public void setup() throws FileNotFoundException {
		fileInputStream = new FileInputStream(ResourceUtils.getFile("classpath:CSV_EXAMPLE.csv"));
		csvParseTransactionService = new CSVParseTransactionService();
	}

	@Test
	public void parseCSV() throws IOException {

		List<TransactionDTO> transactions = csvParseTransactionService.parse(fileInputStream.readAllBytes());

		Assertions.assertThat(transactions)
				.hasSize(1)
				.isNotEmpty()
				.extracting(TransactionDTO::getCompetencia)
				.allSatisfy(competencia -> Assertions.assertThat(competencia).isEqualTo(5L));

		Assertions.assertThat(transactions)
				.extracting(TransactionDTO::getDescricao)
				.containsOnly("DESPESA 02/05/2023");

		Assertions.assertThat(transactions).extracting(TransactionDTO::getTipoRendimento)
				.containsOnly(TipoRendimento.DESPESA);

		Assertions.assertThat(transactions).flatExtracting(TransactionDTO::getDetails)
				.allSatisfy(detail -> {
					Assertions.assertThat(detail.getValor()).isCloseTo(new BigDecimal("120.00"), Offset.offset(new BigDecimal("0.01")));
					Assertions.assertThat(detail.getDescricao()).isEqualTo("Compra no débito - ABC Supermercados");
					Assertions.assertThat(detail.getData()).isEqualTo(LocalDate.of(2023, 5, 2));
				});


	}

}
