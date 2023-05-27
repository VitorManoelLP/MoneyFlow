package com.ofx.parse.imp;

import com.client.common.dto.TransactionDTO;
import com.client.common.enums.TipoArquivo;

import java.util.List;

public interface ParseTransactionHandler {

	boolean isSupported(TipoArquivo tipoArquivo);

	List<TransactionDTO> parse(byte[] file);

}
