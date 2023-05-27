package com.ofx.parse.service;

import com.client.common.dto.TransactionDTO;
import com.client.common.dto.TransactionDetailDTO;
import com.client.common.enums.TipoArquivo;
import com.client.common.enums.TipoRendimento;
import com.ofx.parse.exception.CsvParseException;
import com.ofx.parse.imp.ParseTransactionHandler;
import com.ofx.parse.utils.ParseUtils;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;

@Service
public class CSVParseTransactionService implements ParseTransactionHandler {

	private static final int HEADER_ROW = 1;

	@Override
	public boolean isSupported (TipoArquivo tipoArquivo) {
		return TipoArquivo.CSV.equals(tipoArquivo);
	}

	@SneakyThrows
	public List<TransactionDTO> parse (byte[] file) {

		List<TransactionDTO> transactions = new ArrayList<>();

		try (InputStream inputStream = new ByteArrayInputStream(file)) {

			CSVParser csv = getCsvRecords(inputStream);

			Map<TipoRendimento, List<TransactionDetailDTO>> detailByTipoRendimento = new HashMap<>();

			for (CSVRecord record : csv) {
				extractCSVReports(detailByTipoRendimento, record);
			}

			detailByTipoRendimento.forEach((tipoRendimento, details) -> buildDetails(transactions, tipoRendimento, details));

		} catch (IOException | IllegalArgumentException e) {
			throw new CsvParseException("Erro ao ler arquivo CSV", e);
		}

		return transactions;
	}

	private void extractCSVReports (Map<TipoRendimento, List<TransactionDetailDTO>> detailByTipoRendimento, CSVRecord record) {

		if (record.getRecordNumber() == HEADER_ROW) {
			return;
		}

		LocalDate data = ParseUtils.parseDefault(record.get("Data"), "/");
		BigDecimal valor = ParseUtils.parseBigDecimal(record.get("Valor"));
		String descricao = record.get("Descrição");

		TransactionDetailDTO detail = TransactionDetailDTO.of(descricao, valor.abs(), data);

		identifyType(detailByTipoRendimento, valor, detail);
	}

	private void identifyType (Map<TipoRendimento, List<TransactionDetailDTO>> detailByTipoRendimento, BigDecimal valor, TransactionDetailDTO detail) {
		if (valor.compareTo(BigDecimal.ZERO) < 0) {
			detailByTipoRendimento.computeIfAbsent(TipoRendimento.DESPESA, key -> new ArrayList<>()).add(detail);
		} else {
			detailByTipoRendimento.computeIfAbsent(TipoRendimento.RECEITA, key -> new ArrayList<>()).add(detail);
		}
	}

	private void buildDetails (List<TransactionDTO> transactions, TipoRendimento tipoRendimento, List<TransactionDetailDTO> details) {

		LocalDate lastDate = details.stream()
				.map(TransactionDetailDTO::getData)
				.max(LocalDate::compareTo)
				.orElseThrow(NoSuchElementException::new);

		transactions.add(TransactionDTO.of(
				getDescricao(tipoRendimento, lastDate),
				(long) lastDate.getMonth().getValue(),
				tipoRendimento,
				details)
		);
	}

	private String getDescricao (TipoRendimento tipoRendimento, LocalDate lastDate) {
		return String.format("%s %s", ParseUtils.toUpper(tipoRendimento.getDescricao()), ParseUtils.parseBr(lastDate));
	}

	private CSVParser getCsvRecords (InputStream inputStream) throws IOException {
		return CSVParser.parse(inputStream, Charset.defaultCharset(), CSVFormat.DEFAULT
				.withHeader("Data", "Valor", "Identificador", "Descrição")
				.withIgnoreHeaderCase()
				.withTrim());
	}

}
