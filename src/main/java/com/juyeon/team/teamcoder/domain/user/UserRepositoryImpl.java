package com.juyeon.team.teamcoder.domain.user;

import com.juyeon.team.teamcoder.domain.tagUser.QTagUser;
import com.juyeon.team.teamcoder.domain.tagUser.TagUser;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
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
    public List<TagUser> findTagUserByUser(User userEntity) {
        QUser user = QUser.user;
        QTagUser tagUser = QTagUser.tagUser;
        JPQLQuery query = from(tagUser)
                .where(tagUser.user.eq(userEntity));
        //JPAQueryFactory


        return query.fetch();
    }
}
