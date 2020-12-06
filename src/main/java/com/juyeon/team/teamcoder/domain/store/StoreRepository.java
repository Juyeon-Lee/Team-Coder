package com.juyeon.team.teamcoder.domain.store;

import com.juyeon.team.teamcoder.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(Long id);
    List<Store> findAllByUser_Id(Long id);
    List<Store> findAllByGroup(Group group);
    Optional<Store> findByUser_idAndGroup_id(Long userId, Long groupId);
}
