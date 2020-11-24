package com.juyeon.team.teamcoder.domain.group;

import com.juyeon.team.teamcoder.domain.tag.QTag;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.tagGroup.QTagGroup;
import com.juyeon.team.teamcoder.domain.user.Location;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    public List<Group> findAllByCondition(String aim, String period, int age,
                                          String loc, List<String> tags) {
        QGroup group = QGroup.group;
        QTagGroup tagGroup = QTagGroup.tagGroup;
        QTag tag = QTag.tag;

        BooleanBuilder groupBuilder = new BooleanBuilder();
        BooleanBuilder tagBuilder = new BooleanBuilder();
        if(!aim.isEmpty()){
            groupBuilder.and(group.aim.eq(GroupAim.valueOf(aim)));
        }
        if(!period.isEmpty() && period.equals("POSSIBLE")){ // 무조건 통과: [select 조건 : 전체기간|입력 안함 / db값 : 0(NULL)]
            groupBuilder.andAnyOf(group.workPeriod.startDate.before(LocalDate.now()), group.workPeriod.startDate.isNull());
            groupBuilder.andAnyOf(group.workPeriod.endDate.after(LocalDate.now()),group.workPeriod.endDate.isNull());
        }
        if(age !=0){ // 무조건 통과: [select 조건 : 입력 안함 / db값 : 0(NULL)]
            groupBuilder.andAnyOf(group.ageLimit.maxAge.goe(age), group.ageLimit.maxAge.eq(0));
            groupBuilder.and(group.ageLimit.minAge.loe(age));
        }
        if(!loc.isEmpty() && !loc.equals("ALL")){ // 무조건 통과: [select 조건 : 전체 지역|입력 안함 / db값 : NULL]
            groupBuilder.andAnyOf(group.location.eq(Location.valueOf(loc)),group.location.isNull());
        }
        //tags 검색...
        for(String t : tags){
            tagBuilder.or(tag.name.likeIgnoreCase(t));
        }

        JPQLQuery query = from(group)
                .distinct()
                .where(groupBuilder)
                .innerJoin(group.tagGroups, tagGroup)
                .innerJoin(tagGroup.tag, tag)
                .on(tagBuilder);
        return query.fetch();
    }
}
