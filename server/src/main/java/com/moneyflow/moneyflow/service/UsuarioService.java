package com.moneyflow.moneyflow.service;

import com.client.common.enums.TipoRendimento;
import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.dto.InitialInformationsDTO;
import com.moneyflow.moneyflow.dto.RendimentoTotalDTO;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

	private final UsuarioRendimentoRepository usuarioRendimentoRepository;

	private final UsuarioRepository usuarioRepository;

	private final RendimentoService rendimentoService;

	public InitialInformationsDTO initialInformations (Long id) {

		Usuario usuario = usuarioRepository
				.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		List<UsuarioRendimento> rendimentosByUser = usuarioRendimentoRepository.findAllByUsuarioId(id);

		List<RendimentoTotalDTO> rendimentosTotais = rendimentoService.processarRendimentoByUser(rendimentosByUser);

		return InitialInformationsDTO.builder()
				.nomeUsuario(usuario.getNome())
				.valorTotalDespesas(rendimentoService.getValorTotalBy(rendimentosByUser, TipoRendimento.DESPESA))
				.valorTotalReceitas(rendimentoService.getValorTotalBy(rendimentosByUser, TipoRendimento.RECEITA))
				.rendimentosTotais(rendimentosTotais)
				.build();
	}

}
