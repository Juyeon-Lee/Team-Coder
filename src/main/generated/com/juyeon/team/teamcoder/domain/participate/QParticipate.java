package com.juyeon.team.teamcoder.domain.participate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParticipate is a Querydsl query type for Participate
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QParticipate extends EntityPathBase<Participate> {

    private static final long serialVersionUID = 1059310032L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParticipate participate = new QParticipate("participate");

    public final StringPath applyComment = createString("applyComment");

    public final com.juyeon.team.teamcoder.domain.group.QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<PartiStatus> status = createEnum("status", PartiStatus.class);

    public final com.juyeon.team.teamcoder.domain.user.QUser user;

    public QParticipate(String variable) {
        this(Participate.class, forVariable(variable), INITS);
    }

    public QParticipate(Path<? extends Participate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParticipate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParticipate(PathMetadata metadata, PathInits inits) {
        this(Participate.class, metadata, inits);
    }

    public QParticipate(Class<? extends Participate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new com.juyeon.team.teamcoder.domain.group.QGroup(forProperty("group"), inits.get("group")) : null;
        this.user = inits.isInitialized("user") ? new com.juyeon.team.teamcoder.domain.user.QUser(forProperty("user")) : null;
    }

}

