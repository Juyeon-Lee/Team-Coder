package com.juyeon.team.teamcoder.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 177979344L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> birth = createNumber("birth", Integer.class);

    public final ListPath<com.juyeon.team.teamcoder.domain.group.Group, com.juyeon.team.teamcoder.domain.group.QGroup> createdGroups = this.<com.juyeon.team.teamcoder.domain.group.Group, com.juyeon.team.teamcoder.domain.group.QGroup>createList("createdGroups", com.juyeon.team.teamcoder.domain.group.Group.class, com.juyeon.team.teamcoder.domain.group.QGroup.class, PathInits.DIRECT2);

    public final EnumPath<EduLevel> education = createEnum("education", EduLevel.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Location> location = createEnum("location", Location.class);

    public final StringPath name = createString("name");

    public final StringPath picture = createString("picture");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath roleKey = createString("roleKey");

    public final SetPath<com.juyeon.team.teamcoder.domain.tagUser.TagUser, com.juyeon.team.teamcoder.domain.tagUser.QTagUser> tagUsers = this.<com.juyeon.team.teamcoder.domain.tagUser.TagUser, com.juyeon.team.teamcoder.domain.tagUser.QTagUser>createSet("tagUsers", com.juyeon.team.teamcoder.domain.tagUser.TagUser.class, com.juyeon.team.teamcoder.domain.tagUser.QTagUser.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

