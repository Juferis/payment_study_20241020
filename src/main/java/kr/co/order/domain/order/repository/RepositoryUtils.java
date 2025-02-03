package kr.co.order.domain.order.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

public final class RepositoryUtils {

    public static BooleanExpression containsString(StringPath column, String value) {
        if (value == null) {
            return null;
        }
        return column.contains(value);
    }

    public static BooleanExpression eqNumber(NumberPath<Long> column, Long value) {
        if (value == null) {
            return null;
        }
        return column.eq(value);
    }
}
