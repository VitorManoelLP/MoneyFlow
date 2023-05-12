package com.moneyflow.moneyflow.service;

import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.domain.Usuario;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.dto.InitialInformationsDTO;
import com.moneyflow.moneyflow.dto.RendimentoTotalDTO;
import com.moneyflow.moneyflow.enums.TipoRendimento;
import com.moneyflow.moneyflow.repository.UsuarioRendimentoRepository;
import com.moneyflow.moneyflow.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
