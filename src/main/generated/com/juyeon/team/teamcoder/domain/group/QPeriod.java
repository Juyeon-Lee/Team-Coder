package com.juyeon.team.teamcoder.domain.group;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPeriod is a Querydsl query type for Period
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QPeriod extends BeanPath<Period> {

    private static final long serialVersionUID = 1191767374L;

    public static final QPeriod period = new QPeriod("period");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QPeriod(String variable) {
        super(Period.class, forVariable(variable));
    }

    public QPeriod(Path<? extends Period> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPeriod(PathMetadata metadata) {
        super(Period.class, metadata);
    }

}

