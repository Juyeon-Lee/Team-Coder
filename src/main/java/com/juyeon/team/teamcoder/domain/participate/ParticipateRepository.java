package com.juyeon.team.teamcoder.domain.participate;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ParticipateRepository extends JpaRepository<Participate,Long> {
    Optional<Participate> findById(Long id);

    Optional<Participate> findByUser_idAndGroup_id(Long uid, Long gid);

    //List<Participate> findAllByUser_IdOrderByIdAsc(Long id);

    //List<Participate> findAllByGroup_IdOrderByIdAsc(Long id);
}
