package com.juyeon.team.teamcoder.domain.group;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNum is a Querydsl query type for Num
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QNum extends BeanPath<Num> {

    private static final long serialVersionUID = -1599527143L;

    public static final QNum num = new QNum("num");

    public final NumberPath<Integer> currentNum = createNumber("currentNum", Integer.class);

    public final NumberPath<Integer> maxNum = createNumber("maxNum", Integer.class);

    public QNum(String variable) {
        super(Num.class, forVariable(variable));
    }

    public QNum(Path<? extends Num> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNum(PathMetadata metadata) {
        super(Num.class, metadata);
    }

}

