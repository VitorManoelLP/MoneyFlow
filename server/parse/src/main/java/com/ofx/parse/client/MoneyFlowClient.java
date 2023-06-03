package com.ofx.parse.client;

import com.client.common.dto.RequestParseDTO;
import com.client.common.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "money-flow-api", url = "${money-flow-api-url:http://localhost:8080/money-flow-api}")
public interface MoneyFlowClient {

	@RequestMapping(method = RequestMethod.POST, value = "/api/rendimentos/salvar-extrato")
	void salvarExtrato (List<TransactionDTO> transactions);

}
