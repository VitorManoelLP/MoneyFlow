package com.moneyflow.moneyflow.service;

import com.client.common.enums.TipoRendimento;
import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.dto.RendimentoTotalDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RendimentoService {

	public List<RendimentoTotalDTO> processarRendimentoByUser(List<UsuarioRendimento> rendimentosByUser) {

		Map<Long, List<UsuarioRendimento>> rendimentosByMes = getRendimentosBy(rendimentosByUser, UsuarioRendimento::getCompetencia);

		Map<TipoRendimento, List<UsuarioRendimento>> rendimentosByTipo = getRendimentosBy(rendimentosByUser, UsuarioRendimento::getTipoRendimento);

		List<RendimentoTotalDTO> rendimentosTotais = new ArrayList<>();

		rendimentosByMes.forEach((mes, rendimento) -> processRendimentosByMonth(rendimentosByTipo, rendimentosTotais, mes, rendimento));

		return rendimentosTotais;
	}

	public BigDecimal getValorTotalBy (List<UsuarioRendimento> rendimentos, TipoRendimento tipoRendimento) {
		return getRendimentosBy(rendimentos, UsuarioRendimento::getTipoRendimento).getOrDefault(tipoRendimento, new ArrayList<>())
				.stream()
				.map(UsuarioRendimento::getRendimentos)
				.flatMap(List::stream)
				.map(Rendimento::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private <KEY> Map<KEY, List<UsuarioRendimento>> getRendimentosBy (List<UsuarioRendimento> rendimentosByUser, Function<UsuarioRendimento, KEY> by) {
		return rendimentosByUser.stream().collect(Collectors.groupingBy(by));
	}

	private void processRendimentosByMonth (Map<TipoRendimento, List<UsuarioRendimento>> rendimentosByTipo,
	                                        List<RendimentoTotalDTO> rendimentosTotais,
	                                        Long mes, List<UsuarioRendimento> rendimento) {

		filterAndPopulateRendimentosByMonth(rendimento, rendimentosByTipo, TipoRendimento.RECEITA, rendimentosTotais, mes);
		filterAndPopulateRendimentosByMonth(rendimento, rendimentosByTipo, TipoRendimento.DESPESA, rendimentosTotais, mes);
	}

	private void filterAndPopulateRendimentosByMonth (List<UsuarioRendimento> rendimento,
	                                                  Map<TipoRendimento, List<UsuarioRendimento>> rendimentosByTipo,
	                                                  TipoRendimento despesa,
	                                                  List<RendimentoTotalDTO> rendimentosTotais,
	                                                  Long mes) {
		rendimento.stream()
				.filter(s -> getByTipo(rendimentosByTipo, despesa).contains(s.getId()))
				.forEach(despesas -> populate(rendimentosTotais, mes, despesas, despesa));
	}


	private List<Long> getByTipo (Map<TipoRendimento, List<UsuarioRendimento>> rendimentosByTipo,
	                              TipoRendimento tipoRendimento) {

		return rendimentosByTipo.getOrDefault(tipoRendimento, new ArrayList<>())
				.stream()
				.map(UsuarioRendimento::getId)
				.collect(Collectors.toList());
	}

	private void populate (List<RendimentoTotalDTO> rendimentosTotais,
	                       Long key,
	                       UsuarioRendimento usuarioRendimento,
	                       TipoRendimento tipoRendimento) {

		RendimentoTotalDTO rendimentoGeral = RendimentoTotalDTO.builder()
				.mes(key)
				.idUsuarioRendimento(usuarioRendimento.getId())
				.tipoRendimento(tipoRendimento)
				.valorTotal(getTotalRendimentos(usuarioRendimento.getRendimentos()))
				.nome(usuarioRendimento.getDescricao())
				.build();

		rendimentosTotais.add(rendimentoGeral);
	}


	private BigDecimal getTotalRendimentos (List<Rendimento> value) {
		return value.stream().map(Rendimento::getValor)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
