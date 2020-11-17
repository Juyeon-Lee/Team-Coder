package com.juyeon.team.teamcoder.domain.tag;

import com.juyeon.team.teamcoder.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
