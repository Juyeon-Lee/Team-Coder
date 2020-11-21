package com.juyeon.team.teamcoder.domain.tagUser;

import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TagUserRepository extends JpaRepository<TagUser, Long> {

    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션 사용
    Optional<TagUser> findByTagAndUser(Tag tag, User user);
}
