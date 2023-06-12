package com.moneyflow.moneyflow.resource.abstracts;

import com.moneyflow.moneyflow.service.abstracts.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@RequiredArgsConstructor
public abstract class CrudResource<T, S extends Serializable> {

    private final CrudService<T, S> service;

    @GetMapping
    public ResponseEntity<Page<T>> findAll (@RequestParam("search") String search, Pageable pageable) {
        return ResponseEntity.ok(service.findAll(search, pageable));
    }

}
