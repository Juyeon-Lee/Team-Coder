package com.juyeon.team.teamcoder.domain.group;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = 446159186L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroup group = new QGroup("group1");

    public final QAge ageLimit;

    public final EnumPath<GroupAim> aim = createEnum("aim", GroupAim.class);

    public final SetPath<com.juyeon.team.teamcoder.domain.participate.Participate, com.juyeon.team.teamcoder.domain.participate.QParticipate> applyUsers = this.<com.juyeon.team.teamcoder.domain.participate.Participate, com.juyeon.team.teamcoder.domain.participate.QParticipate>createSet("applyUsers", com.juyeon.team.teamcoder.domain.participate.Participate.class, com.juyeon.team.teamcoder.domain.participate.QParticipate.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final EnumPath<com.juyeon.team.teamcoder.domain.user.EduLevel> education = createEnum("education", com.juyeon.team.teamcoder.domain.user.EduLevel.class);

    public final StringPath file = createString("file");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.juyeon.team.teamcoder.domain.user.Location> location = createEnum("location", com.juyeon.team.teamcoder.domain.user.Location.class);

    public final com.juyeon.team.teamcoder.domain.user.QUser manager;

    public final QNum memberNum;

    public final StringPath name = createString("name");

    public final EnumPath<GroupStatus> status = createEnum("status", GroupStatus.class);

    public final SetPath<com.juyeon.team.teamcoder.domain.tagGroup.TagGroup, com.juyeon.team.teamcoder.domain.tagGroup.QTagGroup> tagGroups = this.<com.juyeon.team.teamcoder.domain.tagGroup.TagGroup, com.juyeon.team.teamcoder.domain.tagGroup.QTagGroup>createSet("tagGroups", com.juyeon.team.teamcoder.domain.tagGroup.TagGroup.class, com.juyeon.team.teamcoder.domain.tagGroup.QTagGroup.class, PathInits.DIRECT2);

    public final QPeriod workPeriod;

    public QGroup(String variable) {
        this(Group.class, forVariable(variable), INITS);
    }

    public QGroup(Path<? extends Group> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroup(PathMetadata metadata, PathInits inits) {
        this(Group.class, metadata, inits);
    }

    public QGroup(Class<? extends Group> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ageLimit = inits.isInitialized("ageLimit") ? new QAge(forProperty("ageLimit")) : null;
        this.manager = inits.isInitialized("manager") ? new com.juyeon.team.teamcoder.domain.user.QUser(forProperty("manager")) : null;
        this.memberNum = inits.isInitialized("memberNum") ? new QNum(forProperty("memberNum")) : null;
        this.workPeriod = inits.isInitialized("workPeriod") ? new QPeriod(forProperty("workPeriod")) : null;
    }

}

