package com.moneyflow.moneyflow.service;

import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioRendimentoService {

	private final UsuarioRendimentoRepository usuarioRendimentoRepository;

	private final UsuarioRepository usuarioRepository;

	public UsuarioRendimento salvar (UsuarioRendimento usuarioRendimento) {
		usuarioRendimento.getRendimentos().forEach(rendimento -> rendimento.assign(usuarioRendimento));
		Usuario usuario = usuarioRepository.findById(1L).orElseThrow();
		usuarioRendimento.assignUser(usuario);
		return usuarioRendimentoRepository.save(usuarioRendimento);
	}
}
