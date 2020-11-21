package com.juyeon.team.teamcoder.domain.group;

import com.juyeon.team.teamcoder.domain.tag.QTag;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.tagGroup.QTagGroup;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepositoryImpl extends QuerydslRepositorySupport
        implements CustomGroupRepository{

    public GroupRepositoryImpl() {
        super(Group.class);
    }

    @Override
    public List<String> findTagByGroup(Group groupEntity) {
        QGroup group = QGroup.group;
        QTagGroup tagGroup = QTagGroup.tagGroup;
        QTag tag = QTag.tag;

        JPQLQuery query = from(group)
                .select(tag.name)
                .where(group.eq(groupEntity))
                .innerJoin(group.tagGroups, tagGroup)
                .innerJoin(tagGroup.tag, tag);

        return query.fetch();
    }

    @Override
    public List<Group> findAllFilterAge(int age){
        QGroup group = QGroup.group;

        BooleanBuilder builder = new BooleanBuilder();
        if(age !=0){
            builder.and(group.ageLimit.maxAge.gt(age));
            builder.and(group.ageLimit.minAge.loe(age));
        }
        // tag ->
        // contains로 체크

        JPQLQuery query = from(group)
                .where(builder);
        return query.fetch();
    }
}
