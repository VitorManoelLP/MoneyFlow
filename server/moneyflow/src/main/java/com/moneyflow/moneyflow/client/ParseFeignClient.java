package com.moneyflow.moneyflow.client;

import com.client.common.dto.RequestParseDTO;
import com.client.common.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "parse", url = "${parse-url:http://localhost:8081}")
public interface ParseFeignClient {

	@RequestMapping(method = RequestMethod.POST, value = "parse")
	List<TransactionDTO> parse (RequestParseDTO requestParseDTO);

}
