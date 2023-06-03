package com.moneyflow.moneyflow.service;

import com.client.common.dto.RequestParseDTO;
import com.client.common.dto.TransactionDTO;
import com.client.common.enums.TipoArquivo;
import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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

	public void salvarExtrato (List<TransactionDTO> transactions) {

		Usuario usuario = usuarioRepository.findById(1L).orElseThrow();

		List<UsuarioRendimento> usuarioRendimentos = transactions.stream()
				.map(transaction -> convertToEntity(usuario, transaction))
				.collect(Collectors.toList());

		usuarioRendimentoRepository.saveAll(usuarioRendimentos);
	}

	private UsuarioRendimento convertToEntity (Usuario usuario, TransactionDTO transaction) {

		List<Rendimento> rendimentos = transaction.getDetails()
				.stream()
				.map(details -> Rendimento.of(
						null,
						details.getDescricao(),
						details.getValor(),
						details.getData(),
						null)
				)
				.collect(Collectors.toList());


		UsuarioRendimento usuarioRendimento = UsuarioRendimento.of(
				null,
				usuario,
				transaction.getDescricao(),
				transaction.getTipoRendimento(),
				rendimentos,
				transaction.getCompetencia()
		);

		rendimentos.forEach(rendimento -> rendimento.assign(usuarioRendimento));

		return usuarioRendimento;
	}
}
