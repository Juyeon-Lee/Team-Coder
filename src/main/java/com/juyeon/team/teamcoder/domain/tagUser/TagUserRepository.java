package com.juyeon.team.teamcoder.domain.tagUser;

import com.juyeon.team.teamcoder.domain.tag.Tag;
import com.juyeon.team.teamcoder.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagUserRepository extends JpaRepository<TagUser, Long> {
    Optional<TagUser> findByTagAndUser(Tag tag, User user);
}
