package com.moneyflow.moneyflow.service.abstracts;

import com.moneyflow.moneyflow.repository.abstracts.CrudRepository;
import com.moneyflow.moneyflow.util.RsqlOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CrudService<T, S extends Serializable> {

    private final CrudRepository<T, S> repository;

    @Transactional(readOnly = true)
    public Page<T> findAll (String search, Pageable page) {

        return repository.findAll((root, query, criteriaBuilder) ->
                getPredicate(search, root, query, criteriaBuilder), page);
    }

    private Predicate getPredicate (final String search, final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        buildSearch(search, root, query, criteriaBuilder);
        return query.getRestriction();
    }

    private void buildSearch (final String search, final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        if (!search.isEmpty()) {

            String rsqlOperator = RsqlOperator.getRsqlOperator(search);

            Map<String, String> fieldValue = getFieldValue(search, rsqlOperator);

            List<Predicate> predicates = new ArrayList<>();

            fieldValue.forEach((field, value) -> predicates.add(RsqlOperator.resolvePredicate(search)
                    .apply(value, root.get(field), criteriaBuilder)));

            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
    }

    private Map<String, String> getFieldValue (final String search, final String rsqlOperator) {
        return Optional.of(search).stream()
                .map(sch -> sch.split(rsqlOperator))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

}
