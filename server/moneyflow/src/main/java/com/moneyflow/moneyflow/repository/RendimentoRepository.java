package com.moneyflow.moneyflow.repository;

import com.moneyflow.moneyflow.domain.Rendimento;
import com.moneyflow.moneyflow.repository.abstracts.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendimentoRepository extends CrudRepository<Rendimento, Long> {

	List<Rendimento> findAllByUsuarioRendimentoId (Long idUsuarioRendimento);

}
