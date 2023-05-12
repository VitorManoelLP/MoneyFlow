package com.moneyflow.moneyflow.repository;

import com.moneyflow.moneyflow.domain.UsuarioRendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRendimentoRepository extends JpaRepository<UsuarioRendimento, Long> {

	List<UsuarioRendimento> findAllByUsuarioId (Long idUsuario);

}
