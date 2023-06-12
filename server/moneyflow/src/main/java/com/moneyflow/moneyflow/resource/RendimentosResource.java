package com.moneyflow.moneyflow.resource;

import com.client.common.dto.TransactionDTO;
import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.dto.RendimentoDTO;
import com.moneyflow.moneyflow.repository.RendimentoRepository;
import com.moneyflow.moneyflow.resource.abstracts.CrudResource;
import com.moneyflow.moneyflow.service.UsuarioRendimentoService;
import com.moneyflow.moneyflow.service.abstracts.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rendimentos")
public class RendimentosResource extends CrudResource<UsuarioRendimento, Long> {

	private final RendimentoRepository rendimentoRepository;

	private final UsuarioRendimentoService usuarioRendimentoService;

	public RendimentosResource (final CrudService<UsuarioRendimento, Long> service,
	                            final RendimentoRepository rendimentoRepository,
	                            final UsuarioRendimentoService usuarioRendimentoService) {
		super(service);
		this.rendimentoRepository = rendimentoRepository;
		this.usuarioRendimentoService = usuarioRendimentoService;
	}

	@GetMapping("/usuario-rendimento/{id}")
	public ResponseEntity<List<RendimentoDTO>> getRendimentosByUsuarioRendimento (@PathVariable Long id) {
		return ResponseEntity.ok(rendimentoRepository.findAllByUsuarioRendimentoId(id)
				.stream()
				.map(Rendimento::convertToDTO)
				.collect(Collectors.toList()));
	}

	@PostMapping
	public ResponseEntity<UsuarioRendimento> salvar (@RequestBody UsuarioRendimento usuarioRendimento) {

		return ResponseEntity.created(ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUri())
				.body(usuarioRendimentoService.salvar(usuarioRendimento));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById (@PathVariable Long id) {
		rendimentoRepository.deleteById(id);
		return ResponseEntity.accepted().build();
	}

	@PostMapping("/salvar-extrato")
	public ResponseEntity<Void> salvarRendimentosOfx(@RequestBody List<TransactionDTO> transactions) {
		usuarioRendimentoService.salvarExtrato(transactions);
		return ResponseEntity.created(ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUri()).build();
	}

}
