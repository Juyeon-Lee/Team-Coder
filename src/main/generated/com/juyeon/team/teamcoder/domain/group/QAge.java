package com.juyeon.team.teamcoder.domain.group;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAge is a Querydsl query type for Age
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAge extends BeanPath<Age> {

    private static final long serialVersionUID = -1599540078L;

    public static final QAge age = new QAge("age");

    public final NumberPath<Integer> maxAge = createNumber("maxAge", Integer.class);

    public final NumberPath<Integer> minAge = createNumber("minAge", Integer.class);

    public QAge(String variable) {
        super(Age.class, forVariable(variable));
    }

    public QAge(Path<? extends Age> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAge(PathMetadata metadata) {
        super(Age.class, metadata);
    }

}

