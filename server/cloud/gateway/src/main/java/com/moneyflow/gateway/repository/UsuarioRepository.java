package com.moneyflow.gateway.repository;

import com.moneyflow.gateway.domain.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario, Long> {

    Mono<Usuario> findByEmail (String email);

    @Transactional
    default Mono<Usuario> findOneByEmail (String email) {
        return findByEmail(email);
    }

}
