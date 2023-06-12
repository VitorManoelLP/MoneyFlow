package com.moneyflow.moneyflow.repository.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CrudRepository<T, S extends Serializable> extends JpaRepository<T, S>, JpaSpecificationExecutor<T> {
}
