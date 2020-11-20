package com.juyeon.team.teamcoder.domain.user;

import com.juyeon.team.teamcoder.domain.tag.QTag;
import com.juyeon.team.teamcoder.domain.tagUser.QTagUser;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport
implements CustomUserRepository{

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public List findTagByUser(User userEntity) {
        QUser user = QUser.user;
        QTagUser tagUser = QTagUser.tagUser;
        QTag tag = QTag.tag;

        // 제일 밑에 fetchjoin을 붙였더니 결과가 3배 중복되어 나왔다.
        /*
        김영한 님의 설명 :
        fetch join을 사용하는 이유는 엔티티 상태에서 엔티티 그래프를 참조하기 위해서 사용하는 것입니다. 따라서 당연히 엔티티가 아닌 DTO 상태로 조회하는 것은 불가능합니다.
        이 경우 fetch join을 사용하지 마시고, 그냥 순수한 join을 사용하시면 원하는 결과를 얻을 수 있습니다^^
         */
        JPQLQuery query = from(user)
                .select(tag.name)
                .where(user.eq(userEntity))
                .innerJoin(user.tagUsers, tagUser)
                .innerJoin(tagUser.tag, tag);

        return query.fetch();
    }
}
