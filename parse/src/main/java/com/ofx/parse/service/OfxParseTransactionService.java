package com.ofx.parse.service;

import com.client.common.dto.TransactionDTO;
import com.client.common.dto.TransactionDetailDTO;
import com.client.common.enums.TipoArquivo;
import com.client.common.enums.TipoRendimento;
import com.ofx.parse.exception.OfxParseException;
import com.ofx.parse.imp.ParseTransactionHandler;
import com.ofx.parse.utils.ParseUtils;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.ResponseMessage;
import com.webcohesion.ofx4j.domain.data.ResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponse;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.domain.data.common.TransactionList;
import com.webcohesion.ofx4j.domain.data.signon.SignonResponseMessageSet;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OfxParseTransactionService implements ParseTransactionHandler {

	@Override
	public boolean isSupported (TipoArquivo tipoArquivo) {
		return TipoArquivo.OFX.equals(tipoArquivo);
	}

	@SneakyThrows
	public List<TransactionDTO> parse (byte[] file) {

		try (InputStream inputStream = new ByteArrayInputStream(file)) {

			AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<>(ResponseEnvelope.class);

			try {

				ResponseEnvelope unmarshal = unmarshaller.unmarshal(inputStream);

				Iterator<ResponseMessageSet> messageSetIterator = unmarshal.getMessageSets().iterator();

				List<TransactionDTO> transactions = new ArrayList<>();

				while(messageSetIterator.hasNext()) {

					ResponseMessageSet next = messageSetIterator.next();

					if(next instanceof SignonResponseMessageSet) {
						continue;
					}

					List<ResponseMessage> responseMessages = next.getResponseMessages();

					responseMessages.forEach(message -> {

						BankStatementResponse bankStatement = ParseUtils.getBankTransaction(message).getMessage();

						TransactionList transactionList = bankStatement.getTransactionList();

						Map<TipoRendimento, List<TransactionDetailDTO>> detailByTipoRendimento = new HashMap<>();

						transactionList.getTransactions().forEach(transaction -> addTransactionDetail(detailByTipoRendimento, transaction));

						extractByTipoRendimento(bankStatement, transactionList, detailByTipoRendimento, TipoRendimento.RECEITA, transactions);
						extractByTipoRendimento(bankStatement, transactionList, detailByTipoRendimento, TipoRendimento.DESPESA, transactions);

					});

				}

				return transactions;

			} catch (IOException | OFXParseException ex) {
				throw new OfxParseException("Erro ao processar arquivo OFX", ex);
			}

		}
	}

	private void extractByTipoRendimento (BankStatementResponse bankStatement,
	                                                TransactionList transactionList,
	                                                Map<TipoRendimento, List<TransactionDetailDTO>> detailByTipoRendimento,
	                                                TipoRendimento tipoRendimento,
	                                                List<TransactionDTO> transactions) {

		if (detailByTipoRendimento.containsKey(tipoRendimento)) {
			List<TransactionDetailDTO> details = detailByTipoRendimento.get(tipoRendimento);

			transactions.add(TransactionDTO.of(
					getDescricao(bankStatement, transactionList, tipoRendimento),
					ParseUtils.getMonth(transactionList.getEnd()),
					tipoRendimento,
					details
			));
		}
	}

	private void addTransactionDetail (Map<TipoRendimento, List<TransactionDetailDTO>> detailByTipoRendimento, Transaction transaction) {

		TipoRendimento tipoRendimento = TipoRendimento.getByIdentificador(transaction.getTransactionType().name());
		detailByTipoRendimento.computeIfAbsent(tipoRendimento, key -> new ArrayList<>()).add(buildDetail(transaction));
	}

	private String getDescricao (BankStatementResponse bankStatement, TransactionList transactionList, TipoRendimento tipoRendimento) {
		return String.format("%s %s %s",
				ParseUtils.toUpper(tipoRendimento.getDescricao()),
				ParseUtils.toUpper(bankStatement.getAccount().getBankId()),
				ParseUtils.parseBrInstant(transactionList.getEnd().toInstant()));
	}

	private TransactionDetailDTO buildDetail (Transaction transaction) {
		return TransactionDetailDTO.of(
				transaction.getMemo(),
				BigDecimal.valueOf(transaction.getAmount()).abs(),
				ParseUtils.ofLocalDate(transaction.getDatePosted().toInstant())
		);
	}



}
