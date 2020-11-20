package com.juyeon.team.teamcoder.domain.group;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findAllByManager(User user);
}
