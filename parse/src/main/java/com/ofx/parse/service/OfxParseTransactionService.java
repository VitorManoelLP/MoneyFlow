package com.ofx.parse.service;

import com.client.common.dto.TransactionDTO;
import com.client.common.enums.TipoRendimento;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfxParseTransactionService {

	public List<TransactionDTO> parseOfx (MultipartFile file) {
		return new ArrayList<>();
	}

}
