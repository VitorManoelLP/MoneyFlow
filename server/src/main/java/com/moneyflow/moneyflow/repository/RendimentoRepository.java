package com.moneyflow.moneyflow.repository;

import com.moneyflow.moneyflow.domain.Rendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendimentoRepository extends JpaRepository<Rendimento, Long> {

	List<Rendimento> findAllByUsuarioRendimentoId (Long idUsuarioRendimento);

}
