package com.moneyflow.moneyflow.resource;

import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.dto.RendimentoDTO;
import com.moneyflow.moneyflow.repository.RendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rendimentos")
@RequiredArgsConstructor
public class RendimentosResource {

	private final RendimentoRepository rendimentoRepository;

	@GetMapping("/usuario-rendimento/{id}")
	public ResponseEntity<List<RendimentoDTO>> getRendimentosByUsuarioRendimento (@PathVariable Long id) {

		return ResponseEntity.ok(rendimentoRepository.findAllByUsuarioRendimentoId(id)
				.stream()
				.map(Rendimento::convertToDTO)
				.collect(Collectors.toList()));
	}

}
