package com.juyeon.team.teamcoder.domain.tagGroup;

import com.juyeon.team.teamcoder.domain.group.Group;
import com.juyeon.team.teamcoder.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TagGroupRepository extends JpaRepository<TagGroup,Long> {
    
    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션 사용
    Optional<TagGroup> findByTagAndGroup(Tag tag, Group group);
}
