package com.moneyflow.moneyflow.resource;

import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.dto.InitialInformationsDTO;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import com.moneyflow.moneyflow.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

	private final UsuarioRepository usuarioRepository;

	private final UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<Usuario> save (@RequestBody @Valid Usuario usuario) {
		return ResponseEntity.created(URI.create("/api/usuarios"))
				.body(usuarioRepository.save(usuario));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Long id) {
		usuarioRepository.deleteById(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findById (@PathVariable Long id) {
		return ResponseEntity.ok(usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado")));
	}

	@GetMapping("/initial-informations/{id}")
	public ResponseEntity<InitialInformationsDTO> findInitialInformations (@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.initialInformations(id));
	}

}
