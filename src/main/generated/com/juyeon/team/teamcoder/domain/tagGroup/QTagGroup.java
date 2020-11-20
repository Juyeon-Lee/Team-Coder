package com.juyeon.team.teamcoder.domain.tagGroup;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTagGroup is a Querydsl query type for TagGroup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTagGroup extends EntityPathBase<TagGroup> {

    private static final long serialVersionUID = -1691478640L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTagGroup tagGroup = new QTagGroup("tagGroup");

    public final com.juyeon.team.teamcoder.domain.group.QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.juyeon.team.teamcoder.domain.tag.QTag tag;

    public QTagGroup(String variable) {
        this(TagGroup.class, forVariable(variable), INITS);
    }

    public QTagGroup(Path<? extends TagGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTagGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTagGroup(PathMetadata metadata, PathInits inits) {
        this(TagGroup.class, metadata, inits);
    }

    public QTagGroup(Class<? extends TagGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new com.juyeon.team.teamcoder.domain.group.QGroup(forProperty("group"), inits.get("group")) : null;
        this.tag = inits.isInitialized("tag") ? new com.juyeon.team.teamcoder.domain.tag.QTag(forProperty("tag")) : null;
    }

}

