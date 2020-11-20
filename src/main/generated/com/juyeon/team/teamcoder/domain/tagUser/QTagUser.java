package com.juyeon.team.teamcoder.domain.tagUser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTagUser is a Querydsl query type for TagUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTagUser extends EntityPathBase<TagUser> {

    private static final long serialVersionUID = -418872802L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTagUser tagUser = new QTagUser("tagUser");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.juyeon.team.teamcoder.domain.tag.QTag tag;

    public final com.juyeon.team.teamcoder.domain.user.QUser user;

    public QTagUser(String variable) {
        this(TagUser.class, forVariable(variable), INITS);
    }

    public QTagUser(Path<? extends TagUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTagUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTagUser(PathMetadata metadata, PathInits inits) {
        this(TagUser.class, metadata, inits);
    }

    public QTagUser(Class<? extends TagUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tag = inits.isInitialized("tag") ? new com.juyeon.team.teamcoder.domain.tag.QTag(forProperty("tag")) : null;
        this.user = inits.isInitialized("user") ? new com.juyeon.team.teamcoder.domain.user.QUser(forProperty("user")) : null;
    }

}

