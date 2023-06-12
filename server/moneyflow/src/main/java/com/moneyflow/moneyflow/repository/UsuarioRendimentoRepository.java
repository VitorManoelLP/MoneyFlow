package com.moneyflow.moneyflow.repository;

import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import com.moneyflow.moneyflow.repository.abstracts.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRendimentoRepository extends CrudRepository<UsuarioRendimento, Long> {

	List<UsuarioRendimento> findAllByUsuarioId (Long idUsuario);

}
