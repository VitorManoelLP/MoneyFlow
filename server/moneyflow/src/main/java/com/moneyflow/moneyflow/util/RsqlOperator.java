package com.moneyflow.moneyflow.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.function.TriFunction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Getter
public enum RsqlOperator {

    EQUAL("==", "=", (value, path, criteriaBuilder) -> criteriaBuilder.equal(path, value)),
    NOT_EQUAL("!=", "<>", (value, path, criteriaBuilder) -> criteriaBuilder.notEqual(path, value)),
    IN("=in=", "IN", (value, path, criteriaBuilder) -> path.in(Arrays.asList(((String) value).split(",")))),
    NOT_IN("=out=", "NOT IN", (value, path, criteriaBuilder) -> path.in(Arrays.asList(((String) value).split(","))).not()),
    LIKE("=like=", "LIKE", (value, path, criteriaBuilder) -> criteriaBuilder.like(path, (String) value)),
    NOT_LIKE("=notlike=", "NOT LIKE", (value, path, criteriaBuilder) -> criteriaBuilder.notLike(path, (String) value));

    private final String rsqlOperator;
    private final String sqlOperator;
    private final TriFunction<Object, Expression<String>, CriteriaBuilder, Predicate> applyPredicate;

    public static TriFunction<Object, Expression<String>, CriteriaBuilder, Predicate> resolvePredicate (String rsql) {
        RsqlOperator operator = getSqlByRsql(rsql);
        return operator.getApplyPredicate();
    }

    public static String getRsqlOperator (String rsql) {
        return EnumUtils.getEnumList(RsqlOperator.class)
                .stream()
                .map(RsqlOperator::getRsqlOperator)
                .filter(rsql::contains)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static RsqlOperator getSqlByRsql (String rsql) {
        return EnumUtils.getEnumList(RsqlOperator.class)
                .stream()
                .filter(operator -> rsql.contains(operator.getRsqlOperator()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
